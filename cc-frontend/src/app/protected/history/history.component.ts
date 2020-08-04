import { Component, OnInit } from '@angular/core';
import {ConvertOperationCase} from "../../model/ConvertOperationCase";
import {AuthenticationService} from "../../authentication/authentication.service";
import {CurrencyService} from "../../service/currency.service";
import {OperationService} from "../../service/operation.service";
import {Currency} from "../../model/Currency";

@Component({
  selector: 'app-history',
  templateUrl: './history.component.html',
  styleUrls: ['./history.component.css']
})
export class HistoryComponent implements OnInit {

  currencies = [];
  operationData: ConvertOperationCase[];
  currencyFrom: Currency;
  currencyTo: Currency;
  operationDate: Date;

  constructor(private auth: AuthenticationService,
              private currencyService: CurrencyService,
              private operationService: OperationService) { }

  ngOnInit(): void {
    this.currencyService.getCurrencies().subscribe(data => {
      this.currencies = data;
      this.currencyFrom = this.currencies[0];
      this.currencyTo = this.currencies[0];
    });
  }

  findData() {
    console.log(this.currencyFrom.id + '  ' + this.currencyTo.id + '  ' + this.operationDate)
    this.operationService.findOperations(this.currencyFrom.id, this.currencyTo.id, this.operationDate)
      .subscribe(data => this.operationData = data);
  }

  logout() {
    this.auth.logout()
  }
}
