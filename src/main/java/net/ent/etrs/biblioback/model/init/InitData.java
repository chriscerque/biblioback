package net.ent.etrs.biblioback.model.init;

import com.github.javafaker.Faker;
import net.ent.etrs.biblioback.model.daos.DaoAuteur;
import net.ent.etrs.biblioback.model.daos.DaoIllustrateur;
import net.ent.etrs.biblioback.model.daos.DaoLivre;
import net.ent.etrs.biblioback.model.daos.DaoUser;
import net.ent.etrs.biblioback.model.daos.exceptions.DaoException;
import net.ent.etrs.biblioback.model.entities.Auteur;
import net.ent.etrs.biblioback.model.entities.Illustrateur;
import net.ent.etrs.biblioback.model.entities.Livre;
import net.ent.etrs.biblioback.model.entities.User;
import net.ent.etrs.biblioback.model.entities.references.Genre;
import net.ent.etrs.biblioback.model.entities.references.Role;
import net.ent.etrs.biblioback.utils.Hash;
import org.apache.commons.collections4.IterableUtils;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;
import java.time.ZoneId;
import java.util.List;
import java.util.Locale;

@Startup
@Singleton
public class InitData {
    private final Faker faker = new Faker(Locale.FRANCE);
    
    @Inject
    private DaoUser daoUser;
    
    @Inject
    private DaoLivre daoLivre;
    
    @Inject
    private DaoAuteur daoAuteur;
    
    @Inject
    private DaoIllustrateur daoIllustrateur;
    
    @PostConstruct
    public void init() {
        try {
            this.initUsers();
            this.initAuteurs();
            this.initLivres();
            this.initIllustrateurs();
        } catch (DaoException e) {
            e.printStackTrace();
        }
    }
    
    private void initUsers() throws DaoException {
        if (this.daoUser.count() == 0) {
            User user = new User();
            user.setLogin("USER");
            user.setPassword(Hash.sha512("USER"));
            user.setRole(Role.USER);
            
            User admin = new User();
            admin.setLogin("ADMIN");
            admin.setPassword(Hash.sha512("ADMIN"));
            admin.setRole(Role.ADMIN);
            
            this.daoUser.save(user);
            this.daoUser.save(admin);
        }
    }
    
    private void initAuteurs() throws DaoException {
        if (this.daoAuteur.count() == 0) {
            for (int i = 0; i < 1000; i++) {
                Auteur a = new Auteur();
                a.setNom(faker.name().lastName());
                a.setPrenom(faker.name().firstName());
                a.setDateDeNaissance(faker.date().birthday().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
                this.daoAuteur.save(a);
            }
        }
    }
    
    private void initLivres() throws DaoException {
        if (this.daoLivre.count() == 0) {
            List<Auteur> auteurList = IterableUtils.toList(this.daoAuteur.findAll());
            for (int i = 0; i < 30; i++) {
                Livre l = new Livre();
                l.setNom(faker.book().title());
                l.setGenre(Genre.values()[faker.random().nextInt(0, Genre.values().length - 1)]);
                l.setIsbn(faker.regexify("[0-9]{10}"));
                l.setDateParution(faker.date().birthday(0, 100).toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
                l.setNbPages(faker.random().nextInt(100, 1500));
                l.setAuteur(auteurList.get(faker.random().nextInt(0, auteurList.size() - 1)));
                this.daoLivre.save(l);
            }
        }
    }
    
    private void initIllustrateurs() throws DaoException {
        if (this.daoIllustrateur.count() == 0) {
            for (int i = 0; i < 30; i++) {
                Illustrateur il = new Illustrateur();
                il.setNom(faker.name().lastName());
                il.setPrenom(faker.name().firstName());
                this.daoIllustrateur.save(il);
            }
        }
    }
    
}
