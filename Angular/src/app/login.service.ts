import { HttpClient ,HttpErrorResponse,HttpHeaders} from '@angular/common/http';
import { Injectable } from '@angular/core';
import { login } from './logincomponent/login';
import { Observable,catchError , throwError} from 'rxjs';
import { admin } from './adminlogin/admin';
import { user } from './create-logins/User';
import { Router } from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class LoginService {

baseurl=""
  constructor(private httpclient:HttpClient,private router:Router) { }


  login(user:login):Observable<any>
  {
    localStorage.clear();
    return this.httpclient.post(`${this.baseurl}/any/user/authenticate`,user,{responseType:'text' as 'json'}).pipe(catchError(this.handleError));
  }
 
 admin(admin:admin):Observable<object>
 {
  return this.httpclient.post(`${this.baseurl}/any/user/authenticate`,admin,{responseType:'text' as 'json'}).pipe(catchError(this.handleError));
 }
   
 createUser(teamId:number,user:user):Observable<object>{
  return this.httpclient.post(`${this.baseurl}/admin/user/${teamId}`,user,{responseType:'text' as 'json'}).pipe(catchError(this.handleError));
 }

 logout():void{
 this.router.navigate(['/login'])
 }

  private handleError(error: HttpErrorResponse) {

    
    if (error.status === 0) {
      // A client-side or network error occurred. Handle it accordingly.
      console.error('An error occurred:', error.error);
    } 
    else {
      // The backend returned an unsuccessful response code.
      // The response body may contain clues as to what went wrong.
      console.error(error.error);
      
    }
    // Return an observable with a user-facing error message.
    console.log(error)
    return throwError(error);
  }
}
