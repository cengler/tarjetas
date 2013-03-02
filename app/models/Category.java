package models;

import com.avaje.ebean.Page;
import play.data.validation.Constraints;
import play.db.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: caeycae
 * Date: 25/02/13
 * Time: 20:27
 * To change this template use File | Settings | File Templates.
 */
@Entity
public class Category extends Model {

    @Id
    public Long id;

    @Constraints.Required
    public String name;

    public static Model.Finder<Long,Category> find = new Model.Finder<Long,Category>(Long.class, Category.class);

    public static Page<Category> page(int page, int pageSize, String sortBy, String order, String filter) {
        return
                find.where()
                        .ilike("name", "%" + filter + "%")
                        .orderBy(sortBy + " " + order)
                        .findPagingList(pageSize)
                        .getPage(page);
    }

    public static Map<String,String> all() {
        LinkedHashMap<String,String> list = new LinkedHashMap<String,String>();
        for(Category c: Category.find.orderBy("name").findList()) {
            list.put(c.id.toString(), c.name);
        }
        return list;
    }
}
