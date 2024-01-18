package com.ap.sports.repository;

import com.ap.sports.domain.Player;
import com.ap.sports.dto.PlayerDto;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PlayerRepository {

    private Session session;
    public List<Player> getPlayersByGenderLevelAndAge(String gender, int level, int age) {
        String hql = "FROM Player WHERE gender = :gender AND level = :level AND age = :age";
        Query<Player> query = session.createQuery(hql, Player.class);
        query.setParameter("gender", gender);
        query.setParameter("level", level);
        query.setParameter("age", age);
        return query.list();
        //Equivalent raw SQL query
//        SELECT * FROM players
//        WHERE gender = 'desired_gender'
//        AND level = desired_level
//        AND age = desired_age;
    }

    public List<Player> findPlayersWithoutSports() {
        String hql = "SELECT p FROM Player p WHERE NOT EXISTS (SELECT 1 FROM PlayerSport ps WHERE ps.player = p)";
        Query<Player> query = session.createQuery(hql, Player.class);

        return query.getResultList();
    }

    public Player findByEmail(String email) {
        return  null;
    }

    public void save(Player player) {
    }

    public List<PlayerDto> findPaginatedPlayersWithSportsFilter(int page, int pageSize, String sportName) {
        String hql = "SELECT new com.example.PlayerDto(p.email, p.name) FROM Player p " +
                "JOIN p.sports s " +
                "WHERE :sportName IS NULL OR s.name = :sportName";

        Query<PlayerDto> query = session.createQuery(hql, PlayerDto.class);
        query.setParameter("sportName", sportName);
        query.setFirstResult(page * pageSize);
        query.setMaxResults(pageSize);

        return query.list();
    }
}
