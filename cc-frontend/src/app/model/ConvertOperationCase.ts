import {Currency} from "./Currency";

export class ConvertOperationCase {
  currencyFrom: Currency;
  currencyTo: Currency;
  valueFrom: number;
  valueTo: number;
  date: Date;

  constructor(currencyFrom: Currency, currencyTo: Currency, valueFrom: number, valueTo: number, date: Date) {
    this.currencyFrom = currencyFrom;
    this.currencyTo = currencyTo;
    this.valueFrom = valueFrom;
    this.valueTo = valueTo;
    this.date = date;
  }
}
