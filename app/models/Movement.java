package models;

import com.avaje.ebean.Page;
import com.avaje.ebean.PagingList;
import com.avaje.ebean.Query;
import play.data.validation.Constraints;
import play.db.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.util.Date;

@Entity
public class Movement extends Model {

    public enum MovementType{INGRESO,EGRESO};

    @Id
    public Long id;

    @Constraints.Required
    public MovementType ptype;

    @Constraints.Required
    public String description;

    @Constraints.Required
    public Double amount;

    @Constraints.Required
    public Date date;

    @Constraints.Required
    @ManyToOne
    public Category category;

    @Constraints.Required
    @ManyToOne
    public Account account;

    @ManyToOne
    public Closure closure;

    public static Finder<Long,Movement> find = new Finder<Long,Movement>(Long.class, Movement.class);

    public static Page<Movement> page(int page, int pageSize, String sortBy, String order,
                                      String filter, Long accountId, Long closureId) {
        Query query = find.where().ilike("description", "%" + filter + "%").orderBy(sortBy + " " + order);
        if(accountId != null && accountId != 0)
            query.where().eq("account.id", accountId);
        if(closureId != null && closureId != 0)
            query.where().eq("closure.id", closureId);
        return query.findPagingList(pageSize).getPage(page);
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPtype(MovementType ptype) {
        this.ptype = ptype;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public void setClosure(Closure closure) {
        this.closure = closure;
    }
}
