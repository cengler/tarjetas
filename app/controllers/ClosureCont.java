
package controllers;

import com.avaje.ebean.Filter;
import models.Category;
import models.Closure;
import models.Movement;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.listClosures;
import views.html.editClosureForm;
import views.html.createClosureForm;

import java.util.List;

import static play.data.Form.form;

/**
 * Created with IntelliJ IDEA.
 * User: caeycae
 * Date: 28/02/13
 * Time: 23:40
 * To change this template use File | Settings | File Templates.
 */
public class ClosureCont extends Controller {

    public static Result GO_CLOSURE = redirect(
            routes.ClosureCont.listClosures(0, "date", "asc", "")
    );

    public static Result listClosures(int page, String sortBy, String order, String filter) {
        return ok(listClosures.render(
                models.Closure.page(page, 10, sortBy, order, filter),
                sortBy, order, filter)
        );
    }

    public static Result createClosure() {
        Form<Closure> closureForm = form(Closure.class);
        return ok(
                createClosureForm.render(closureForm)
        );
    }

    public static Result saveClosure() {
        Form<Closure> closureForm = form(Closure.class).bindFromRequest();
        if(closureForm.hasErrors()) {
            return badRequest(createClosureForm.render(closureForm));
        }
        Closure c = closureForm.get();

        // obtengo gastos de la tarjeta TODO ver de solo tomar movimientos despues de la ultima clausura
        Filter<Movement> movementsFilter = Movement.find.filter();
        movementsFilter.eq("account.id", c.account.id);
        List<Movement> movements = movementsFilter.filter(Movement.find.all());
        Double total = 0d;
        for (Movement m : movements) {
            if(m.ptype == Movement.MovementType.EGRESO)
                total += m.amount;
            else
                total -= m.amount;
        }
        c.amount = total;

        c.save();

        flash("success", "El cierre de la cuenta " + c.account.name  + "[" + c.account.number + "] ha sido efectuado");
        return GO_CLOSURE;
    }

    public static Result editClosure(Long id) {
        return ok(editClosureForm.render(id));
    }


    public static Result deleteClosure(Long id) {
        Closure.find.ref(id).delete();
        flash("success", "El cierre ha sido borrado");
        return GO_CLOSURE;
    }
}
