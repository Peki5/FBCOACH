package hr.fer.fbcoach.service;

import hr.fer.fbcoach.model.TacticsApplication;

import java.util.List;

public interface TacticsApplicationService {
    List<TacticsApplication> getAllTacticsApplications();
    TacticsApplication getTacticsApplicationById(Long id);
    TacticsApplication addTacticsApplication(TacticsApplication tacticsApplication);
    TacticsApplication updateTacticsApplication(Long id, TacticsApplication tacticsApplicationDetails);
    void deleteTacticsApplication(Long id);
    List<TacticsApplication> getTacticsApplicationsByMatchId(Long matchId);
    List<TacticsApplication> getTacticsApplicationsByTacticsId(Long tacticsId);
    List<TacticsApplication> getTacticsApplicationsByTeamId(Long teamId);






}
