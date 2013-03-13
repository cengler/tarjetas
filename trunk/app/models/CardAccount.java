package models;

import com.avaje.ebean.Filter;
import com.avaje.ebean.Page;
import play.db.ebean.Model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Transient;

import play.data.format.*;
import play.data.validation.*;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Entity
@DiscriminatorValue(value="card")
public class CardAccount extends Account {

    public interface All {}

    public enum Day {
        LUNES, MARTES, MIERCOLES, JUEVES, VIERNES, SABADO, DOMINGO
    }

    @Constraints.Required
    @Formats.DateTime(pattern="yyyy-MM")
    public Date since;

    @Constraints.Required
    @Formats.DateTime(pattern="yyyy-MM")
    public Date through;

    @Constraints.Required
    public Integer closeWeekNumber;

    @Constraints.Required
    public Day closeWeekday;

    @Constraints.Required
    public Integer payDay;

    @Transient
    private Double amount;

    /**
     * Generic query helper for entity CardAccount with id Long
     */
    public static Model.Finder<Long,CardAccount> find = new Model.Finder<Long,CardAccount>(Long.class, CardAccount.class);

    public static Page<CardAccount> page(int page, int pageSize, String sortBy, String order, String filter) {
        return
                find.where()
                        .ilike("name", "%" + filter + "%")
                        .orderBy(sortBy + " " + order)
                        .findPagingList(pageSize)
                        .getPage(page);
    }

    public Double getAmount() {
        Filter<Movement> movementsFilter = Movement.find.filter();
        movementsFilter.eq("account.id", id);
        List<Movement> movements = movementsFilter.filter(Movement.find.all());
        Double total = 0d;
        for (Movement m : movements) {
            if(m.ptype == Movement.MovementType.EGRESO)
                total += m.amount;
            else
                total -= m.amount;
        }
        return total;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }
}
