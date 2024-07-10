package org.example.bo;

import org.example.bo.custom.IMPL.*;

public class BOFactory {
    private static BOFactory boFactory;
    private BOFactory(){
    }
    public static BOFactory getBoFactory(){
        return (boFactory==null)? boFactory=new BOFactory() : boFactory;
    }

    public enum BOTypes{
      VOTER,LOCATION,ELECTION,ELECTION_VOTER_DETAIL,CANDIDATE,AREA,ELECTION_PARTY,PARTY,ENDELECTION,VOTING,VOTE
    }


    public SuperBO getBO(BOTypes types){
        switch (types){
            case VOTER:
                return new VoterBOImpl();
            case LOCATION:
                return new LocationBOImpl();
            case ELECTION:
                return new ElectionBOImpl();
            case ELECTION_VOTER_DETAIL:
                return new Election_voter_detailBOImpl();
            case CANDIDATE:
                return new CandidateBOImpl();
            case AREA:
                return new AreaBOImpl();
            case ELECTION_PARTY:
                return new ElectionPartyDetailBOlImpl();
            case PARTY:
                return new PartyBOImpl();
            case ENDELECTION:
                return new EndElectionBOImpl();
            case VOTING:
                return new VotingBOImpl();
            case VOTE:
                return new VoteBOImpl();
            default:
                return null;
        }
    }
}
