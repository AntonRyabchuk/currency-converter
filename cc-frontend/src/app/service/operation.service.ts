import {Injectable} from '@angular/core';
import {Observable} from 'rxjs';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {environment} from "../../environments/environment";
import {ConvertOperation} from "../model/ConvertOperation";



@Injectable({
  providedIn: 'root'
})

export class OperationService {
  constructor(private http: HttpClient) {
  }

  getConvertResult(operation: ConvertOperation): Observable<number> {
    console.log('service getConvertResult - ' + operation.valueFrom);
    const requestUrl = environment.AUTH_HOST + `/convert/calc?currencyIdFrom=${operation.currencyIdFrom}&currencyIdTo=${operation.currencyIdTo}&valueFrom=${operation.valueFrom}`;
    return this.http.get<number>(requestUrl);
  }

  public createConvertOperation(operation: ConvertOperation): Observable<ConvertOperation> {
    // const httpOptions = {
    //   headers: new HttpHeaders({'Content-Type': 'application/json', 'Authorization': 'my-auth-token'})
    // };
    const url = environment.AUTH_HOST + '/convert/create';
    return this.http.post<ConvertOperation>(url, operation);
  }
}
