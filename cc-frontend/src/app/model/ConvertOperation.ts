export class ConvertOperation {
  currencyIdFrom: string;
  currencyIdTo: string;
  valueFrom: number;

  constructor(currencyIdFrom: string, currencyIdTo: string, valueFrom: number) {
    this.currencyIdFrom = currencyIdFrom;
    this.currencyIdTo = currencyIdTo;
    this.valueFrom = valueFrom;
  }
}
