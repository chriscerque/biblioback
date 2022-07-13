package net.ent.etrs.biblioback.model.daos.impl;


import net.ent.etrs.biblioback.model.daos.DaoAuteur;
import net.ent.etrs.biblioback.model.daos.JpaBaseDao;
import net.ent.etrs.biblioback.model.entities.Auteur;

import javax.persistence.TypedQuery;
import javax.swing.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

public class DaoAuteurImpl extends JpaBaseDao<Auteur, Serializable> implements DaoAuteur {
    @Override
    public List<Auteur> findAll(int first, int pageSize, Map<String, String> sortedBy, Map<String, String> filterBy) {

        String sql = "SELECT a FROM Auteur a WHERE 1=1 ";

        String nom = null;
        String prenom = null;
        LocalDate dateDeNaissance = null;

        if (filterBy.containsKey("nom")) {
            nom = filterBy.get("nom");
        }

        if (filterBy.containsKey("prenom")) {
            prenom = filterBy.get("prenom");
        }

        if (filterBy.containsKey("dateDeNaissance")) {
            dateDeNaissance = LocalDate.parse(filterBy.get("dateDeNaissance"), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        }

        if (nom != null) {
            sql += " AND a.nom LIKE :nom ";
        }

        if (prenom != null) {
            sql += " AND a.prenom LIKE :prenom ";
        }

        if (dateDeNaissance != null) {
            sql += " AND a.dateDeNaissance = :dateDeNaissance ";
        }

        if (!sortedBy.isEmpty()) {
            sql += " ORDER BY ";
            for (Map.Entry<String, String> e : sortedBy.entrySet()) {
                sql += " a." + e.getKey() + " " + e.getValue() + ",";
            }
            sql = sql.substring(0, sql.length()-1);

        } else {
            sql += " ORDER BY a.nom ASC ";
        }

        TypedQuery<Auteur> q = this.em.createQuery(sql, Auteur.class);

        if (nom != null) {
            q.setParameter("nom", nom+"%");
        }

        if (prenom != null) {
            q.setParameter("prenom", prenom+"%");
        }

        if (dateDeNaissance != null) {
            q.setParameter("dateDeNaissance", dateDeNaissance);
        }

        q.setFirstResult(first);
        q.setMaxResults(pageSize);

        return q.getResultList();
    }

    @Override
    public int count(Map<String, String> filterBy) {

        String sql = "SELECT COUNT(a) FROM Auteur a WHERE 1=1 ";

        String nom = null;
        String prenom = null;
        LocalDate dateDeNaissance = null;

        if (filterBy.containsKey("nom")) {
            nom = filterBy.get("nom");
        }

        if (filterBy.containsKey("prenom")) {
            prenom = filterBy.get("prenom");
        }

        if (filterBy.containsKey("dateDeNaissance")) {
            dateDeNaissance = LocalDate.parse(filterBy.get("dateDeNaissance"), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        }

        if (nom != null) {
            sql += " AND a.nom LIKE :nom ";
        }

        if (prenom != null) {
            sql += " AND a.prenom LIKE :prenom ";
        }

        if (dateDeNaissance != null) {
            sql += " AND a.dateDeNaissance = :dateDeNaissance ";
        }

        TypedQuery<Long> q = this.em.createQuery(sql, Long.class);

        if (nom != null) {
            q.setParameter("nom", nom+"%");
        }

        if (prenom != null) {
            q.setParameter("prenom", prenom+"%");
        }

        if (dateDeNaissance != null) {
            q.setParameter("dateDeNaissance", dateDeNaissance);
        }

        return q.getSingleResult().intValue();
    }
}
