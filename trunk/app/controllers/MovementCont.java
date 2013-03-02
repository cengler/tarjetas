package controllers;

import models.*;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.*;

import java.util.Calendar;
import java.util.Date;

import static play.data.Form.form;

/**
 * Created with IntelliJ IDEA.
 * User: caeycae
 * Date: 23/02/13
 * Time: 18:05
 * To change this template use File | Settings | File Templates.
 */
public class MovementCont extends Controller {

    public static Result GO_MOVEMENT = redirect(
            routes.MovementCont.listMovements(0, "date", "asc", "", 0)
    );

    public static Result listMovements(int page, String sortBy, String order, String filter, Long accountId) {
        return ok(listMovements.render(
                models.Movement.page(page, 10, sortBy, order, filter, accountId),
                sortBy, order, filter, accountId)
        );
    }

    public static Result savePayment() {
        Form<Payment> paymentForm = form(Payment.class).bindFromRequest();
        if(paymentForm.hasErrors()) {
            return badRequest(createPaymentForm.render(paymentForm));
        }
        Payment payment = paymentForm.get();

        // fix por si viene en null (no selecciono nada)
        if(payment.installment == null)
            payment.installment = false;

        if(payment.installment)
        {
            for(int i=0; i<payment.quantity; i++)
            {
                Movement m = new Movement();
                m.setAccount(payment.account);
                m.setAmount(payment.amount / payment.quantity);
                m.setDate(addMonth(payment.date, i));  // TODO
                m.setDescription(payment.description + " cta:" + i);
                m.setPtype(payment.ptype);
                m.setCategory(payment.category);
                m.save();
            }
        } else {
            Movement m = new Movement();
            m.setAccount(payment.account);
            m.setAmount(payment.amount);
            m.setDate(payment.date);
            m.setDescription(payment.description);
            m.setPtype(payment.ptype);
            m.setCategory(payment.category);
            m.save();
        }

        flash("success", "El movimiento " + paymentForm.get().description + " ha sido creado");
        return GO_MOVEMENT;
    }

    public static Result createPayment() {
        Form<Payment> paymentForm = form(Payment.class);
        return ok(
                createPaymentForm.render(paymentForm)
        );
    }

    public static Date addMonth(Date date, Integer i)
    {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.MONTH, i);
        return c.getTime();
    }

    public static Result editMovement(Long id) {
        Form<Movement> movementForm = form(Movement.class).fill(
                Movement.find.byId(id)
        );
        return ok(
                editMovementForm.render(id, movementForm)
        );
    }

    public static Result deleteMovement(Long id) {
        Movement.find.ref(id).delete();
        flash("success", "El movimiento ha sido borrado");
        return GO_MOVEMENT;
    }

    public static Result updateMovement(Long id) {
        Form<Movement> movementForm = form(Movement.class).bindFromRequest();
        if(movementForm.hasErrors()) {
            return badRequest(editMovementForm.render(id, movementForm));
        }
        movementForm.get().update(id);
        flash("success", "El movimiento " + movementForm.get().description + " ha sido actualizada");
        return GO_MOVEMENT;
    }
}
