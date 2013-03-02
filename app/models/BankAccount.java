package models;

import com.avaje.ebean.Page;
import play.db.ebean.Model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue(value="bank")
public class BankAccount extends Account {

    public interface All {}

    /**
     * Generic query helper for entity CardAccount with id Long
     */
    public static Model.Finder<Long,BankAccount> find = new Model.Finder<Long,BankAccount>(Long.class, BankAccount.class);

    public static Page<BankAccount> page(int page, int pageSize, String sortBy, String order, String filter) {
        return
                find.where()
                        .ilike("name", "%" + filter + "%")
                        .orderBy(sortBy + " " + order)
                        .findPagingList(pageSize)
                        .getPage(page);
    }
}
