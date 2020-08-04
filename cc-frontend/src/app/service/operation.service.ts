import {Injectable} from '@angular/core';
import {Observable} from 'rxjs';
import {HttpClient} from '@angular/common/http';
import {environment} from "../../environments/environment";
import {ConvertOperation} from "../model/ConvertOperation";
import {ConvertOperationCase} from "../model/ConvertOperationCase";


@Injectable({
  providedIn: 'root'
})

export class OperationService {

  constructor(private http: HttpClient) {
  }

  getConvertResult(operation: ConvertOperation): Observable<number> {
    const url = environment.AUTH_HOST + `/convert/calc?currencyIdFrom=${operation.currencyIdFrom}&currencyIdTo=${operation.currencyIdTo}&valueFrom=${operation.valueFrom}`;
    return this.http.get<number>(url);
  }

  public createConvertOperation(operation: ConvertOperation): Observable<ConvertOperation> {
    const url = environment.AUTH_HOST + '/convert/create';
    return this.http.post<ConvertOperation>(url, operation);
  }

  findOperations(currencyIdFrom: string, currencyIdTo: string, date: Date): Observable<ConvertOperationCase[]> {
    const url = environment.AUTH_HOST + `/convert/find?currencyIdFrom=${currencyIdFrom}&currencyIdTo=${currencyIdTo}&date=${date}`;
    return this.http.get<ConvertOperationCase[]>(url);
  }
}
