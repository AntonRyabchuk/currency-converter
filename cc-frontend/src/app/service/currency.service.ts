import {Injectable} from '@angular/core';
import {Observable} from 'rxjs';
import {HttpClient} from '@angular/common/http';
import {Currency} from "../model/Currency";
import {environment} from "../../environments/environment";

@Injectable({
  providedIn: 'root'
})

export class CurrencyService {
  constructor(private http: HttpClient) {
  }

  getCurrencies(): Observable<Currency[]> {
    const requestUrl = environment.AUTH_HOST + `/currency/get-all`;
    return this.http.get<Currency[]>(requestUrl);
  }
}
