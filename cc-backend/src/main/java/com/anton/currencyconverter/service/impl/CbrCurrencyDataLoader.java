package com.anton.currencyconverter.service.impl;

import com.anton.currencyconverter.domain.entity.Currency;
import com.anton.currencyconverter.domain.entity.Rate;
import com.anton.currencyconverter.domain.repository.CurrencyRepository;
import com.anton.currencyconverter.service.api.CurrencyDataLoader;
import com.anton.currencyconverter.service.exceptions.CurrencyException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.text.NumberFormat;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CbrCurrencyDataLoader implements CurrencyDataLoader {

    private final CurrencyRepository currencyRepository;

    @Autowired
    public CbrCurrencyDataLoader(CurrencyRepository currencyRepository) {
        this.currencyRepository = currencyRepository;
    }

    @Override
    public List<Currency> getStartCurrencyData() {
        try {
            var document = getDocument();
            return getCurrenciesFromXml(document);
        } catch (Exception ex) {
            log.warn("IN getCurrencyRateData - no currency data received");
            throw new CurrencyException("no currency data received", ex);
        }
    }

    @Override
    public List<Rate> getTodayRateData() {
        try {
            var document = getDocument();
            return getRatesFromXml(document);
        } catch (Exception ex) {
            log.warn("IN getCurrencyRateData - no currency data received");
            throw new CurrencyException("no currency data received", ex);
        }
    }

    private Document getDocument() throws ParserConfigurationException, SAXException, IOException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        var document = builder.parse(getRequestUrl(LocalDate.now()));
        document.getDocumentElement().normalize();
        return document;
    }

    private List<Currency> getCurrenciesFromXml(Document document) {
        var nodeList = document.getElementsByTagName("Valute");
        var currencies = new ArrayList<Currency>();

        for (int i = 0; i < nodeList.getLength(); i++) {

            Node node = nodeList.item(i);

            if (node.getNodeType() == Node.ELEMENT_NODE) {
                var elem = (Element) node;
                var currency = parseCurrencyFromXmlElement(elem);
                currencies.add(currency);
            }
        }
        return currencies;
    }

    private List<Rate> getRatesFromXml(Document document) throws ParseException {
        var nodeList = document.getElementsByTagName("Valute");
        var rates = new ArrayList<Rate>();
        var currencies = currencyRepository.findAll().stream()
                .collect(Collectors.toMap(Currency::getId, currency -> currency));

        for (int i = 0; i < nodeList.getLength(); i++) {

            Node node = nodeList.item(i);

            if (node.getNodeType() == Node.ELEMENT_NODE) {
                var element = (Element) node;
                var currencyIp = element.getAttributes().getNamedItem("ID").getTextContent();
                if (currencies.containsKey(currencyIp)) {
                    var currency = currencies.get(currencyIp);
                    var rate = parseRateFromXmlElement(element, currency);
                    rates.add(rate);
                }
            }
        }
        return rates;
    }

    private Currency parseCurrencyFromXmlElement(Element element) {
        var currency = new Currency();
        currency.setId(element.getAttributes().getNamedItem("ID").getTextContent());
        currency.setNumCode(Integer.parseInt(element.getElementsByTagName("NumCode").item(0).getTextContent()));
        currency.setCharCode(element.getElementsByTagName("CharCode").item(0).getTextContent());
        currency.setName(element.getElementsByTagName("Name").item(0).getTextContent());
        return currency;
    }

    private Rate parseRateFromXmlElement(Element element, Currency currency) throws ParseException {
        var rate = new Rate();
        rate.setCurrency(currency);
        rate.setNominal(Integer.parseInt(element.getElementsByTagName("Nominal").item(0).getTextContent()));

        var format = NumberFormat.getInstance(Locale.FRANCE);
        var value = format.parse(element.getElementsByTagName("Value").item(0).getTextContent());
        rate.setRate(value.doubleValue());

        var today = Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant());
        rate.setDate(today);

        return rate;
    }

    private String getRequestUrl(LocalDate date) {
        var source_data_url = "http://www.cbr.ru/scripts/XML_daily.asp";
        var requestParam = "?date_req=";
        var today = date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        return source_data_url + requestParam + today;
    }
}
