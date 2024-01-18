package com.ap.sports.repository;

import com.ap.sports.domain.PlayerSport;
import com.ap.sports.domain.Sport;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class SportRepository {

    private Session session;

    public List<Sport> getSportsWithNoPlayers() {
        String hql = "SELECT s FROM Sport s WHERE NOT EXISTS (SELECT 1 FROM Player p WHERE p.sport = s)";
        Query<Sport> query = session.createQuery(hql, Sport.class);
        return query.list();
        //Equivalent raw SQL query
//        SELECT sports.*
//        FROM sports
//        WHERE NOT EXISTS (
//                SELECT 1
//                FROM player_sports
//                WHERE player_sports.sport_id = sports.id
    }

    public List<Sport> getSportsWithMultiplePlayers(int minimumPlayerCount) {
        String hql = "SELECT s FROM Sport s JOIN s.players p GROUP BY s HAVING COUNT(p) >= :minimumPlayerCount";
        Query<Sport> query = session.createQuery(hql, Sport.class);
        query.setParameter("minimumPlayerCount", minimumPlayerCount);
        return query.list();
        //Equivalent raw SQL query
//        SELECT sports.*
//        FROM sports
//        JOIN player_sports ON sports.id = player_sports.sport_id
//        GROUP BY sports.id
//        HAVING COUNT(player_sports.player_email) >= 2;
    }

    public List<PlayerSport> getSportsWithAssociatedPlayers(List<String> sportNames) {
        String hql = "SELECT DISTINCT ps FROM PlayerSport ps " +
                "JOIN FETCH ps.sport s " +
                "JOIN FETCH ps.player p " +
                "WHERE s.name IN :sportNames";

        Query<PlayerSport> query = session.createQuery(hql, PlayerSport.class);
        query.setParameter("sportNames", sportNames);
        return query.getResultList();
    }

    public List<Sport> findByNameIn(List<String> sportNames) {
        return new ArrayList<Sport>();
    }

    public Optional<Sport> findById(Long id) {

        String hql = "FROM Sport WHERE id = :id";
        Query<Sport> query = session.createQuery(hql, Sport.class);
        query.setParameter("id", id);

        return Optional.ofNullable(query.uniqueResult());
    }

    public void delete(Long id) {
        String hqlDeletePlayerSports = "DELETE FROM PlayerSport WHERE sport.id = :id";
        String hqlDeleteSport = "DELETE FROM Sport WHERE id = :id";

        session.createQuery(hqlDeletePlayerSports)
                .setParameter("id", id)
                .executeUpdate();

        session.createQuery(hqlDeleteSport)
                .setParameter("id", id)
                .executeUpdate();
    }
}
