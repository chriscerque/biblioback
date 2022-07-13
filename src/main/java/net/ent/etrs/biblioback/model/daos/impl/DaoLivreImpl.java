package net.ent.etrs.biblioback.model.daos.impl;


import net.ent.etrs.biblioback.model.daos.DaoLivre;
import net.ent.etrs.biblioback.model.daos.JpaBaseDao;
import net.ent.etrs.biblioback.model.entities.Livre;
import net.ent.etrs.biblioback.model.entities.references.Genre;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.swing.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class DaoLivreImpl extends JpaBaseDao<Livre, Serializable> implements DaoLivre {
    @Override
    public Optional<Livre> findByIdWithIllustrateurs(Long id) {
        TypedQuery<Livre> q = this.em.createQuery("SELECT l FROM Livre l LEFT JOIN FETCH l.illustrateurs WHERE l.id = :id", Livre.class);
        q.setParameter("id", id);
        
        try {
            return Optional.of(q.getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }
    
    @Override
    public List<Livre> findAll(int first, int pageSize, Map<String, String> sortedBy, Map<String, String> filterBy) {
        String sql = "SELECT l FROM Livre l WHERE 1=1 ";

        String nom = null;
        String isbn = null;
        LocalDate dateParution = null;
        Integer nbPages = null;
        String auteurNomPrenom = null;
        Genre genre = null;

        if (filterBy.containsKey("nom")) {
            nom = filterBy.get("nom");
        }

        if (filterBy.containsKey("isbn")) {
            isbn = filterBy.get("isbn");
        }

        if (filterBy.containsKey("dateParution")) {
            dateParution = LocalDate.parse(filterBy.get("dateParution"), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        }

        if (filterBy.containsKey("nbPages")) {
            nbPages = Integer.parseInt(filterBy.get("nbPages"));
        }

        if (filterBy.containsKey("auteurNomPrenom")) {
            auteurNomPrenom = filterBy.get("auteurNomPrenom");
        }

        if (filterBy.containsKey("genre")) {
            genre = Genre.valueOf(filterBy.get("genre"));
        }

        if (nom != null) {
            sql += " AND l.nom LIKE :nom ";
        }

        if (isbn != null) {
            sql += " AND l.isbn LIKE :isbn ";
        }

        if (dateParution != null) {
            sql += " AND l.dateParution = :dateParution ";
        }

        if (nbPages != null) {
            sql += " AND l.nbPages = :nbPages ";
        }

        if (auteurNomPrenom != null) {
            sql += " AND (l.auteur.nom LIKE :auteurNomPrenom OR l.auteur.prenom LIKE :auteurNomPrenom) ";
        }

        if (genre != null) {
            sql += " AND l.genre = :genre ";
        }

        if (!sortedBy.isEmpty()) {
            sql += " ORDER BY ";
            for (Map.Entry<String, String> e : sortedBy.entrySet()) {
                sql += " l." + e.getKey() + " " + e.getValue() + ",";
            }
            sql = sql.substring(0, sql.length() - 1);

        } else {
            sql += " ORDER BY l.nom ASC ";
        }

        TypedQuery<Livre> q = this.em.createQuery(sql, Livre.class);

        if (nom != null) {
            q.setParameter("nom", nom + "%");
        }

        if (isbn != null) {
            q.setParameter("isbn", isbn + "%");
        }

        if (dateParution != null) {
            q.setParameter("dateParution", dateParution);
        }

        if (nbPages != null) {
            q.setParameter("nbPages", nbPages);
        }

        if (auteurNomPrenom != null) {
            q.setParameter("auteurNomPrenom", auteurNomPrenom + "%");
        }

        if (genre != null) {
            q.setParameter("genre", genre);
        }

        q.setFirstResult(first);
        q.setMaxResults(pageSize);

        return q.getResultList();
    }

    @Override
    public int count(Map<String, String> filterBy) {
        String sql = "SELECT COUNT(l) FROM Livre l WHERE 1=1 ";

        String nom = null;
        String isbn = null;
        LocalDate dateParution = null;
        Integer nbPages = null;
        String auteurNomPrenom = null;
        Genre genre = null;
    
        if (filterBy.containsKey("nom")) {
            nom = filterBy.get("nom");
        }
    
        if (filterBy.containsKey("isbn")) {
            isbn = filterBy.get("isbn");
        }
    
        if (filterBy.containsKey("dateParution")) {
            dateParution = LocalDate.parse(filterBy.get("dateParution"), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        }
    
        if (filterBy.containsKey("nbPages")) {
            nbPages = Integer.parseInt(filterBy.get("nbPages"));
        }
    
        if (filterBy.containsKey("auteurNomPrenom")) {
            auteurNomPrenom = filterBy.get("auteurNomPrenom");
        }
    
        if (filterBy.containsKey("genre")) {
            genre = Genre.valueOf(filterBy.get("genre"));
        }

        if (nom != null) {
            sql += " AND l.nom LIKE :nom ";
        }

        if (isbn != null) {
            sql += " AND l.isbn LIKE :isbn ";
        }

        if (dateParution != null) {
            sql += " AND l.dateParution = :dateParution ";
        }

        if (nbPages != null) {
            sql += " AND l.nbPages = :nbPages ";
        }

        if (auteurNomPrenom != null) {
            sql += " AND (l.auteur.nom LIKE :auteurNomPrenom OR l.auteur.prenom LIKE :auteurNomPrenom) ";
        }

        if (genre != null) {
            sql += " AND l.genre = :genre ";
        }

        TypedQuery<Long> q = this.em.createQuery(sql, Long.class);

        if (nom != null) {
            q.setParameter("nom", nom + "%");
        }

        if (isbn != null) {
            q.setParameter("isbn", isbn + "%");
        }

        if (dateParution != null) {
            q.setParameter("dateParution", dateParution);
        }

        if (nbPages != null) {
            q.setParameter("nbPages", nbPages);
        }

        if (auteurNomPrenom != null) {
            q.setParameter("auteurNomPrenom", auteurNomPrenom + "%");
        }

        if (genre != null) {
            q.setParameter("genre", genre);
        }

        return q.getSingleResult().intValue();
    }
}
