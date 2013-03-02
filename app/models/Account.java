package models;

import play.data.validation.Constraints;
import play.db.ebean.Model;

import javax.persistence.*;
import java.util.LinkedHashMap;
import java.util.Map;

@Entity
@Inheritance(strategy= InheritanceType.JOINED)
@DiscriminatorColumn(name = "type")
public class Account extends Model {

    public interface All {}

    @Id
    public Long id;

    @Constraints.Required
    public String name;

    @Constraints.Required
    public Long number;

    public static Map<String,String> all() {
        LinkedHashMap<String,String> list = new LinkedHashMap<String,String>();
        for(Account c: Account.find.orderBy("name").findList()) {
            list.put(c.id.toString(), c.name);
        }
        return list;
    }

    public static Model.Finder<Long,Account> find = new Model.Finder<Long,Account>(Long.class, Account.class);
}
