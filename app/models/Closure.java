package models;

import com.avaje.ebean.Page;
import play.data.format.Formats;
import play.data.validation.Constraints;
import play.db.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: caeycae
 * Date: 28/02/13
 * Time: 23:34
 * To change this template use File | Settings | File Templates.
 */
@Entity
public class Closure extends Model {

    @Id
    public Long id;

    @ManyToOne
    @Constraints.Required
    public Account account;

    @Constraints.Required
    @Formats.DateTime(pattern="yyyy-MM-dd")
    public Date date;

    public Double amount;

    @OneToMany
    public List<Movement> movements;

    public static Finder<Long,Closure> find = new Finder<Long,Closure>(Long.class, Closure.class);

    public static Page<Closure> page(int page, int pageSize, String sortBy, String order, String filter) {
        return
                find.where()
                        .ilike("account.name", "%" + filter + "%")
                        .orderBy(sortBy + " " + order)
                        .findPagingList(pageSize)
                        .getPage(page);
    }

}
