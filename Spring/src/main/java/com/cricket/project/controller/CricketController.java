package com.cricket.project.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cricket.project.Dto.TeamPlayerDto;
import com.cricket.project.Exception.NotEnoughAmount;
import com.cricket.project.Exception.PlayerAlreadyExsists;
import com.cricket.project.Exception.PlayerNotFound;
import com.cricket.project.Exception.TeamNotFound;
import com.cricket.project.entity.Cricketer;

import com.cricket.project.service.CricketService;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class CricketController {

	@Autowired
	CricketService crs;
	 @PostMapping("/admin/cricketer/{id}")
	 @PreAuthorize("hasAuthority('ROLE_ADMIN')")
	 ResponseEntity<?> add(@PathVariable int id,@RequestBody Cricketer data) 
	 {
		 try
		 {
		   crs.addDetails(id,data);
		 }
		 catch(TeamNotFound t)
		 {
			 return new ResponseEntity<>(t.getMessage(),HttpStatus.NOT_ACCEPTABLE);
		 }
		 catch(PlayerAlreadyExsists p)
		 {
			 return new ResponseEntity<>(p.getMessage(),HttpStatus.ALREADY_REPORTED);
		 }
		 catch(NotEnoughAmount amount)
		 {
			 return new ResponseEntity<>(amount.getMessage(),HttpStatus.BAD_REQUEST);
		 }
		 
		 catch (Exception e) {
			// TODO: handle exception
			 return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_ACCEPTABLE);
		}
		 return new  ResponseEntity<>(HttpStatus.OK);
		 
	 }
	 
	 @GetMapping("/common/AllCricketers")
	 @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
	 List<TeamPlayerDto> showAll()
	 {
		 return crs.displayAll();
	 }
	 @PutMapping("/admin/edit/{id}")
	 @PreAuthorize("hasAuthority('ROLE_ADMIN')")
	 ResponseEntity<?> edit(@PathVariable int id,@RequestBody Cricketer cricketer)
	 {
		 try
		 {
		 crs.update(id, cricketer);
		 }
		 catch (NotEnoughAmount e) {
			// TODO: handle exception
			 return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_ACCEPTABLE);
		}
		 catch (Exception e) {
				// TODO: handle exception
				 return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
			}
		 return new ResponseEntity<>(HttpStatus.ACCEPTED);
	 }
	 @DeleteMapping("/user/delete/{id}")
	 @PreAuthorize("hasAuthority('ROLE_USER')")
	 void remove(@PathVariable int id)
	 {
		 crs.delete(id);
	 }
	 @DeleteMapping("/admin/deletePlayer/{id}")
	 @PreAuthorize("hasAuthority('ROLE_ADMIN')")
	 void delete(@PathVariable int id)
	 {
		 crs.deletePlayer(id);
	 }
	 @GetMapping("/Players/{name}")
	 List<Cricketer> getPLayers(@PathVariable String name)
	 {
		
		 return crs.getPlayersByTeamName(name);
	 }
	 @GetMapping("/Player/TeamDetails/{name}")
	 ResponseEntity<String> getTeamName(@PathVariable String name)
	 {
		 try {
			 return new ResponseEntity<>(crs.getTeamDetails(name),HttpStatus.FOUND);
		 }
		 catch (TeamNotFound e) {
			// TODO: handle exception
			 return new ResponseEntity<String>("TeamNotFound",HttpStatus.NOT_FOUND);
		}
		 catch(PlayerNotFound p)
		 {
			 return new ResponseEntity<String>("Player NotFound",HttpStatus.NOT_FOUND);
		 }
		 catch(Exception e)
		 {
			 
		 }
		 return new ResponseEntity<String>("Exception occured",HttpStatus.NOT_FOUND);
	 }
	 
	 @GetMapping("/common/players/{id}")
	 @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
	 List<Cricketer> getPlayers(@PathVariable int id)
	 {
		 
		 return crs.PlayersByTeamid(id);
	 }
	 @GetMapping("/common/player/{id}")
	 @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
	 Cricketer getPlayer(@PathVariable int id)
	 {
		 return crs.getPlayer(id);
	 }
	 
	 @GetMapping("/common/unAssignedPlayers")
	// @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
	 List<Cricketer> getAll(){
		 return crs.getUnAssignedPlayers();
	 }
	 
	 
	 @PostMapping("admin/addPlayer")
	 @PreAuthorize("hasAuthority('ROLE_ADMIN')")
	 ResponseEntity<?> addPlayer(@RequestBody Cricketer player)
	 {
		 
		 try {
			 crs.addPlayer(player);
		 }
		 catch (PlayerAlreadyExsists e) {
			// TODO: handle exception
			 return new ResponseEntity<>(e.getMessage(),HttpStatus.ALREADY_REPORTED);
		}
		 catch (Exception e) {
			// TODO: handle exception
		}
		 return new ResponseEntity<>(HttpStatus.ACCEPTED);
	 }
	 
	 @PostMapping("/user/assignPlayer/{playerId}/{teamId}")
	 @PreAuthorize("hasAuthority('ROLE_USER')")
	 ResponseEntity<?> addPlayerToTeam(@PathVariable int playerId,@PathVariable int teamId)
	 {
		 try 
		 {
			 crs.addPlayerToTeam(teamId, playerId);
		 }
		 catch (TeamNotFound e) {
			// TODO: handle exception
			 return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_ACCEPTABLE);
		}catch (NotEnoughAmount e) {
			// TODO: handle exception
			return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_ACCEPTABLE);
		}
		 catch (ExpiredJwtException e) {
				// TODO: handle exception
				 return new ResponseEntity<>("Token Expired",HttpStatus.UNAUTHORIZED);
			}
		 catch (Exception e) {
			// TODO: handle exception
			 System.out.println(e);
			 return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_ACCEPTABLE);
		}
		 return new ResponseEntity<>(HttpStatus.ACCEPTED);
		 
	 }
	 
	 @GetMapping("/user/myPlayers")
	 @PreAuthorize("hasAuthority('ROLE_USER')")
	 ResponseEntity<Cricketer> myPlayers(HttpServletRequest request){
		 String userName=request.getUserPrincipal().getName();
		try {
			return new ResponseEntity( crs.getMyPlayers(userName),HttpStatus.OK);
		} catch (TeamNotFound e) {
			// TODO Auto-generated catch block
			return new ResponseEntity(e.getMessage(),HttpStatus.BAD_REQUEST);
		}
		 
	 }
}
