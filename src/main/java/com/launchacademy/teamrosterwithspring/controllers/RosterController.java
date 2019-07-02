package com.launchacademy.teamrosterwithspring.controllers;

import com.launchacademy.teamrosterwithspring.models.League;
import com.launchacademy.teamrosterwithspring.models.Team;
import java.util.HashMap;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/")
public class RosterController {

  @GetMapping("/")
  public ModelAndView getListofAllTeams() {
    ModelAndView modelAndView = new ModelAndView("rosters/showTeams");

    League newLeague = new League().getLeague();
    List<Team> teamList = newLeague.getTeams();

    modelAndView.addObject("teams", teamList);
    return modelAndView;
  }

  @RequestMapping(value = "/teams/{id}")
  @ResponseBody
  public ModelAndView getTeamInfo(@PathVariable("id") int id) {

    League newLeague = new League().getLeague();
    Team requestedTeam = new Team();

    //Index out of range
    if (id >= newLeague.getTeams().size()) {
      throw new HttpClientErrorException(HttpStatus.NOT_FOUND);

    } else {
      for (int i = 0; i < newLeague.getTeams().size(); i++) {
        if (i == id) {
          requestedTeam = newLeague.getTeams().get(i);
        }
      }

      List requestedTeamPlayers = requestedTeam.getPlayers();
      ModelAndView modelAndView = new ModelAndView("rosters/showTeam");
      modelAndView.addObject("team", requestedTeam);
      modelAndView.addObject("players", requestedTeamPlayers);
      return modelAndView;
    }
  }
}
