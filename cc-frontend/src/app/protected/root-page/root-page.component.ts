import {Component, OnInit} from '@angular/core';
import {AuthenticationService} from "../../authentication/authentication.service";
import {CurrencyService} from "../../service/currency.service";
import {OperationService} from "../../service/operation.service";
import {ConvertOperation} from "../../model/ConvertOperation";

@Component({
  selector: 'app-root-page',
  templateUrl: './root-page.component.html',
  styleUrls: ['./root-page.component.css']
})
export class RootPageComponent implements OnInit {

  currencies = [];
  currencyFrom;
  currencyTo;
  valueFrom: number;
  valueTo: number;

  constructor(private auth: AuthenticationService,
              private currencyService: CurrencyService,
              private operationService: OperationService) {
  }

  ngOnInit(): void {
    this.currencyService.getCurrencies().subscribe(data => {
      this.currencies = data;
      this.currencyFrom = this.currencies[0];
      this.currencyTo = this.currencies[0];
      this.valueFrom = 0;
      this.valueTo = 0;
    });
  }

  onChange(event) {
    let operation = new ConvertOperation(this.currencyFrom.id, this.currencyTo.id, this.valueFrom);
    this.operationService.getConvertResult(operation).subscribe(result => {
      this.valueTo = result;
    });
  }

  logout() {
    this.auth.logout()
  }

  saveOperation() {
    this.operationService.createConvertOperation(new ConvertOperation(this.currencyFrom.id, this.currencyTo.id, this.valueFrom));
  }
}
