import { HttpClient, HttpErrorResponse, HttpRequest, HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { team } from './create-team/team';
import { Observable,catchError,pipe,throwError } from 'rxjs';
import { TeamDto } from './teams/TeamDto';



@Injectable({
  providedIn: 'root'
})
export class TeamsService {

  baseUrl=""
  constructor(private http:HttpClient) { }

  addTeam(team:team):Observable<Object>{
    console.log(localStorage.getItem('token'))
   return this.http.post(`${this.baseUrl}/admin/team`,team,{responseType:'text' as 'json'});
  //  .pipe(catchError(this.handleError));
  }
  getTeams():Observable<team[]>{
    console.log(localStorage.getItem('token'))
    return this.http.get<team[]>(`${this.baseUrl}/common/allTeams`,{responseType:'text' as 'json'});
  }
    getOnlyTeams():Observable<TeamDto[]>{
      console.log(localStorage.getItem('token'))
      console.log()
      return this.http.get<TeamDto[]>(`${this.baseUrl}/common/allTeams`);
    }
  getTeam(id:number):Observable<team>{
    return this.http.get<team>(`${this.baseUrl}/common/teams/${id}`)
  }
  getAvailableTeams():Observable<TeamDto[]>{
    return this.http.get<TeamDto[]>(`${this.baseUrl}/admin/available/teams`);
  }

  updateTeam(id:number,team:team):Observable<Object>
  {
    return this.http.put(`${this.baseUrl}/admin/Team/${id}`,team,{responseType:'text' as 'json'}).pipe(catchError(this.handleError));
  }

  deleteTeam(id:number):Observable<Object>
  {
    return this.http.delete(`${this.baseUrl}/admin/Team/${id}`).pipe(catchError(this.handleError))
  }

  getMyTeam():Observable<any>{
       return this.http.get<any>(`${this.baseUrl}/user/myteam`).pipe(catchError(this.handleError));
  }

  private handleError(error: HttpErrorResponse) {

    
    if (error.status === 0) {
      // A client-side or network error occurred. Handle it accordingly.
      console.error('An error occurred:', error.error);
    } 

    else {
      // The backend returned an unsuccessful response code.
      // The response body may contain clues as to what went wrong.
      console.error(error.message);
      
    }
    // Return an observable with a user-facing error message.
  
    return throwError(error);
  }
}
