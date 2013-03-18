
package controllers;

import com.avaje.ebean.Filter;
import com.avaje.ebean.Query;
import models.Category;
import models.Closure;
import models.Movement;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.listClosures;
import views.html.editClosureForm;
import views.html.createClosureForm;

import java.util.Date;
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

        // OBTENGO LA FECHA DEL ULTIMO CIERRE PARA ESA CUENTA
        Closure lastClosure = null;
        Query closureQuery = Closure.find.query();
        closureQuery.setMaxRows(1);
        closureQuery.where().eq("account.id", c.account.id);
        closureQuery.where().setOrderBy("date desc");
        List<Closure> list = closureQuery.where().findList();
        if(list.size() != 0)
            lastClosure = list.get(0);

        // OBTENGO TODOS LOS MOVIMIENTOS DE ESA CUENTA QUE SEAN POSTERIORES AL ULTIMO CIERRE
        Query movementQuery = Movement.find.query();
        movementQuery.where().eq("account.id", c.account.id);
        if(lastClosure != null)
            movementQuery.where().ge("date", lastClosure.date);
        List<Movement> movements = movementQuery.findList();

        // OBTENGO EL MONTO DISPONIBLE EN LA CUENTA
        Double saldo = 0d;
        if(lastClosure != null)
            saldo = lastClosure.amount;
        for (Movement m : movements) {
            if(m.ptype == Movement.MovementType.EGRESO)
                saldo -= m.amount;
            else
                saldo += m.amount;
        }
        c.amount = saldo;

        // SALVO EL CIERRE Y ACTUALIZO LOS MOVIMIENTOS A ESE CIERRE
        c.save();
        for (Movement m : movements) {
            m.setClosure(c);
            m.save();
        }

        flash("success", "El cierre de la cuenta " + c.account.name  + "[" + c.account.number + "] ha sido efectuado para " + movements.size() + " movimientos");
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
