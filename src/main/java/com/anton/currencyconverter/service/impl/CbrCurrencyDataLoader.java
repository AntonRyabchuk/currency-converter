package com.anton.currencyconverter.service.impl;

import com.anton.currencyconverter.model.Currency;
import com.anton.currencyconverter.service.api.CurrencyDataLoader;
import com.anton.currencyconverter.service.exceptions.CurrencyException;
import lombok.extern.slf4j.Slf4j;
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
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Service
@Slf4j
public class CbrCurrencyDataLoader implements CurrencyDataLoader {

    @Override
    public List<Currency> getTodayCurrencyRateData() {
        try {
            Document doc = getDocument();
            doc.getDocumentElement().normalize();
            return getCurrenciesFromXml(doc);
        } catch (Exception ex) {
            log.warn("IN getCurrencyRateData - no currency data received");
            throw new CurrencyException("no currency data received", ex);
        }
    }

    private Document getDocument() throws ParserConfigurationException, SAXException, IOException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        return builder.parse(getRequestUrl(LocalDate.now()));
    }

    private List<Currency> getCurrenciesFromXml(Document doc) throws ParseException {
        var updated = new SimpleDateFormat("dd.MM.yyyy").parse(doc.getElementsByTagName("ValCurs").item(0).getAttributes().getNamedItem("Date").getTextContent());
        var nodeList = doc.getElementsByTagName("Valute");
        var currencies = new ArrayList<Currency>();

        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);

            if (node.getNodeType() == Node.ELEMENT_NODE) {
                var elem = (Element) node;
                Currency currency = parseCurrencyFromXmlElement(elem);
                currency.setUpdated(updated);
                currencies.add(currency);
            }
        }
        return currencies;
    }

    private Currency parseCurrencyFromXmlElement(Element elem) throws ParseException {
        var currency = new Currency();
        currency.setId(elem.getAttributes().getNamedItem("ID").getTextContent());
        currency.setNumCode(Integer.parseInt(elem.getElementsByTagName("NumCode").item(0).getTextContent()));
        currency.setCharCode(elem.getElementsByTagName("CharCode").item(0).getTextContent());
        currency.setNominal(Integer.parseInt(elem.getElementsByTagName("Nominal").item(0).getTextContent()));
        currency.setName(elem.getElementsByTagName("Name").item(0).getTextContent());

        var format = NumberFormat.getInstance(Locale.FRANCE);
        var value = format.parse(elem.getElementsByTagName("Value").item(0).getTextContent());
        currency.setValue(value.doubleValue());
        return currency;
    }

    private String getRequestUrl(LocalDate date) {
        var source_data_url = "http://www.cbr.ru/scripts/XML_daily.asp";
        var requestParam = "?date_req=";
        var today = date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        return source_data_url + requestParam + today;
    }
}
