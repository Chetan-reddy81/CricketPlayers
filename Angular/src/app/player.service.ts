import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { player } from './add-players/player';
import { Observable, catchError, throwError } from 'rxjs';
import { playerTeam } from './all-players/playerteam';

@Injectable({
  providedIn: 'root'
})
export class PlayerService {

baseUrl="";
  constructor(private http:HttpClient) { }

  addPlayer(id:number,player:player):Observable<object>{
    return this.http.post(`${this.baseUrl}/admin/cricketer/${id}`,player).pipe(catchError(this.handleError));
  }

  getPlayersByTeamId(id:number):Observable<player[]>{
    return this.http.get<player[]>(`${this.baseUrl}/common/players/${id}`).pipe(catchError(this.handleError));
  }

  getPlayer(id:number):Observable<player>{
    return this.http.get<player>(`${this.baseUrl}/common/player/${id}`);
  }

  update(id:number,player:player):Observable<object>{
    return this.http.put(`${this.baseUrl}/admin/edit/${id}`,player).pipe(catchError(this.handleError));
  }
  deletePlayer(id:number):Observable<Object>{
    return this.http.delete(`${this.baseUrl}/admin/deletePlayer/${id}`);
  }
  delete(id:number):Observable<object>{
    return this.http.delete(`${this.baseUrl}/user/delete/${id}`);
  }
  addcrickter(player:player):Observable<object>{
    return this.http.post(`${this.baseUrl}/admin/addPlayer`,player,{responseType:'text' as 'json'}).pipe(catchError(this.handleError));
  }

  getUnsoldPlayers():Observable<player[]>{
    return this.http.get<player[]>(`${this.baseUrl}/common/unAssignedPlayers`)
  }
  getAllPlayers():Observable<playerTeam[]>{
    return this.http.get<playerTeam[]>(`${this.baseUrl}/common/AllCricketers`)
  }

  assignPlayer(teamId:number,playerId:number):Observable<Object>
  {
    return this.http.post(`${this.baseUrl}/user/assignPlayer/${playerId}/${teamId}`,{}).pipe(catchError(this.handleError));
  }

  getMyPlayers():Observable<player[]>{
    return this.http.get<player[]>(`${this.baseUrl}/user/myPlayers`)
  }
  private handleError(error: HttpErrorResponse) {

    
    if (error.status === 0) {
      // A client-side or network error occurred. Handle it accordingly.
      console.error('An error occurred:', error.error);
    } 
    else {
      // The backend returned an unsuccessful response code.
      // The response body may contain clues as to what went wrong.
      console.error(error);
      
    }
    // Return an observable with a user-facing error message.
  
    return throwError(error);
  }
}
