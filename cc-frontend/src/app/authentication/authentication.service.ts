import {Injectable} from '@angular/core';
import {HttpClient, HttpErrorResponse} from '@angular/common/http';
import {Observable} from 'rxjs';
import {catchError, map, switchMap, tap} from 'rxjs/operators';
import {TokenStorage} from './token-storage.service';
import {LoginFrom} from './data/login-from';
import {environment} from '../../environments/environment';

interface AccessData {
  access_token: string;
  refresh_token: string;
}

@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {

  constructor(
    private http: HttpClient,
    private tokenStorage: TokenStorage
  ) {
  }

  /**
   * Check, if user already authorized.
   * @description Should return Observable with true or false values
   * @memberOf AuthService
   */
  public isAuthorized(): Observable<boolean> {
    return this.tokenStorage
      .getAccessToken()
      .pipe(map(token => !!token));
  }

  /**
   * Get access token
   * @description Should return access token in Observable from e.g.
   * localStorage
   */
  public getAccessToken(): Observable<string> {
    return this.tokenStorage.getAccessToken();
  }

  /**
   * Function, that should perform refresh token verifyTokenRequest
   * @description Should be successfully completed so interceptor
   * can execute pending requests or retry original one
   */
  public refreshToken(): Observable<AccessData> {
    return this.tokenStorage
      .getRefreshToken()
      .pipe(
        switchMap((refreshToken: string) =>
          this.http.post(`${environment.AUTH_HOST}/auth/refresh`, {refreshToken})
        ),
        tap((tokens: AccessData) => this.saveAccessData(tokens)),
        catchError((err) => {
          this.logout();
          return Observable.throw(err);
        })
      );
  }

  /**
   * Function, checks response of failed request to determine,
   * whether token be refreshed or not.
   * @description Essentialy checks status
   */
  public refreshShouldHappen(response: HttpErrorResponse): boolean {
    return response.status === 401;
  }

  /**
   * Verify that outgoing request is refresh-token,
   * so interceptor won't intercept this request
   */
  public verifyTokenRequest(url: string): boolean {
    return url.endsWith('/refresh');
  }

  /**
   * EXTRA AUTH METHODS
   */

  public login(form: LoginFrom): Observable<any> {
    return this.http.post(`${environment.AUTH_HOST}/auth/login`, form)
      .pipe(
        tap((tokens: AccessData) => this.saveAccessData(tokens)),
      );
  }


  /**
   * Logout
   */
  public logout(): void {
    this.tokenStorage.clear();
    location.reload(true);
  }

  /**
   * Save access data in the storage
   */
  private saveAccessData({access_token, refresh_token}: AccessData) {
    this.tokenStorage
      .setAccessToken(access_token)
      .setRefreshToken(refresh_token);
  }

}
