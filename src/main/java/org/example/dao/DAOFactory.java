package org.example.dao;


import org.example.bo.custom.IMPL.VoterBOImpl;
import org.example.dao.custom.LocationDAO;
import org.example.dao.custom.impl.*;

public class DAOFactory {
    private static DAOFactory daoFactory;

    private DAOFactory() {
    }

    public static DAOFactory getDaoFactory() {
        return (daoFactory == null) ? daoFactory = new DAOFactory() : daoFactory;
    }

    public enum DAOTypes {
        LOCATION,ELECTION,VOTER,CANDIATE,AREA,ELECTION_VOTER,VOTE,VOTE_LIST,PARTY
    }

    public SuperDAO getDAO(DAOTypes types){
        switch (types) {
            case LOCATION:
                return new LocationDAOImpl();
            case ELECTION:
                return new ElectionDAOImpl();
            case VOTER:
                return new VoterDAOImpl();
            case CANDIATE:
                return new CandidateDAOImpl();
            case AREA:
                return  new AreaDAOImpl();
            case ELECTION_VOTER:
                return new Election_voter_detailDAOImpl();
            case VOTE:
                return new VoteDAOImpl();
            case VOTE_LIST:
                return new VotingDAOImpl();
            case PARTY:
                return new PartyDAOImpl();
            default:
                return null;
        }
    }


}
