package controllers;

import models.Category;
import models.Movement;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.categoryAmountReport;
import views.html.createCategoryForm;
import views.html.editCategoryForm;
import views.html.listCategorys;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static play.data.Form.form;
import static play.libs.Json.newObject;
import static play.libs.Json.toJson;

/**
 * Created with IntelliJ IDEA.
 * User: caeycae
 * Date: 25/02/13
 * Time: 20:31
 * To change this template use File | Settings | File Templates.
 */
public class CategoryCont extends Controller {

    ///////////////////////////////////////////
    // CATEGORY
    ///////////////////////////////////////////

    public static Result GO_CATEGORY = redirect(
            routes.CategoryCont.listCategorys(0, "name", "asc", "")
    );

    public static Result listCategorys(int page, String sortBy, String order, String filter) {
        return ok(listCategorys.render(
                Category.page(page, 10, sortBy, order, filter),
                sortBy, order, filter)
        );
    }

    public static Result editCategory(Long id) {
        Form<Category> categoryForm = form(Category.class).fill(
                Category.find.byId(id)
        );
        return ok(
                editCategoryForm.render(id, categoryForm)
        );
    }

    public static Result updateCategory(Long id) {
        Form<Category> categoryForm = form(Category.class).bindFromRequest();
        if(categoryForm.hasErrors()) {
            return badRequest(editCategoryForm.render(id, categoryForm));
        }
        categoryForm.get().update(id);
        flash("success", "La tarjeta " + categoryForm.get().name + " ha sido actualizada");
        return GO_CATEGORY;
    }

    public static Result saveCategory() {
        Form<Category> categoryForm = form(Category.class).bindFromRequest();
        if(categoryForm.hasErrors()) {
            return badRequest(createCategoryForm.render(categoryForm));
        }
        categoryForm.get().save();
        flash("success", "La tarjeta " + categoryForm.get().name + " ha sido creada");
        return GO_CATEGORY;
    }

    public static Result createCategory() {
        Form<Category> categoryForm = form(Category.class);
        return ok(
                createCategoryForm.render(categoryForm)
        );
    }

    public static Result deleteCategory(Long id) {
        Category.find.ref(id).delete();
        flash("success", "La tarjeta ha sido borrada");
        return GO_CATEGORY;
    }

    public static Result categoryAmountReport(int page, String sortBy, String order, String filter) {
        return ok(categoryAmountReport.render(
                Category.page(page, 10, sortBy, order, filter),
                sortBy, order, filter)
        );
    }

    public static Result getCategoryAmount()
    {
        List<Movement> list = Movement.find.all();
        Map<String, Double> map = new HashMap<String, Double>();
        for (Movement m : list)
        {
            if(m.ptype == Movement.MovementType.EGRESO)
            {
                if( map.containsKey(m.category.name) )
                    map.put(m.category.name, map.get(m.category.name) + m.amount);
                else
                    map.put(m.category.name, m.amount);
            }
        }
        return ok(toJson(map.entrySet()));
    }
}
