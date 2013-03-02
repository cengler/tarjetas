package controllers;

import models.BankAccount;
import models.CardAccount;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.*;

import static play.data.Form.form;

/**
 * Manage a database of computers
 */
public class Account extends Controller {

    ///////////////////////////////////////////
    // CARD
    ///////////////////////////////////////////

    public static Result GO_CARD = redirect(
            routes.Account.listCards(0, "name", "asc", "")
    );

    public static Result listCards(int page, String sortBy, String order, String filter) {
        return ok(listCards.render(
                CardAccount.page(page, 10, sortBy, order, filter),
                sortBy, order, filter)
        );
    }

    public static Result editCard(Long id) {
        Form<CardAccount> cardForm = form(CardAccount.class).fill(
                CardAccount.find.byId(id)
        );
        return ok(
                editCardForm.render(id, cardForm)
        );
    }

    public static Result updateCard(Long id) {
        Form<CardAccount> cardForm = form(CardAccount.class).bindFromRequest();
        if(cardForm.hasErrors()) {
            return badRequest(editCardForm.render(id, cardForm));
        }
        cardForm.get().update(id);
        flash("success", "La tarjeta " + cardForm.get().name + " ha sido actualizada");
        return GO_CARD;
    }

    public static Result saveCard() {
        Form<CardAccount> cardForm = form(CardAccount.class).bindFromRequest();
        if(cardForm.hasErrors()) {
            return badRequest(createCardForm.render(cardForm));
        }
        cardForm.get().save();
        flash("success", "La tarjeta " + cardForm.get().name + " ha sido creada");
        return GO_CARD;
    }

    public static Result createCard() {
        Form<CardAccount> cardForm = form(CardAccount.class);
        return ok(
                createCardForm.render(cardForm)
        );
    }

    public static Result deleteCard(Long id) {
        CardAccount.find.ref(id).delete();
        flash("success", "La tarjeta ha sido borrada");
        return GO_CARD;
    }

    ///////////////////////////////////////////
    // BANK
    ///////////////////////////////////////////

    public static Result GO_BANK = redirect(
            routes.Account.listBanks(0, "name", "asc", "")
    );


    public static Result listBanks(int page, String sortBy, String order, String filter) {
        return ok(listBanks.render(
                BankAccount.page(page, 10, sortBy, order, filter),
                sortBy, order, filter)
        );
    }

    public static Result editBank(Long id) {
        Form<BankAccount> bankForm = form(BankAccount.class).fill(
                BankAccount.find.byId(id)
        );
        return ok(
                editBankForm.render(id, bankForm)
        );
    }

    public static Result updateBank(Long id) {
        Form<BankAccount> bankForm = form(BankAccount.class).bindFromRequest();
        if(bankForm.hasErrors()) {
            return badRequest(editBankForm.render(id, bankForm));
        }
        bankForm.get().update(id);
        flash("success", "La cuenta " + bankForm.get().name + " ha sido actualizada");
        return GO_BANK;
    }

    public static Result createBank() {
        Form<BankAccount> bankForm = form(BankAccount.class);
        return ok(
                createBankForm.render(bankForm)
        );
    }

    public static Result saveBank() {
        Form<BankAccount> bankForm = form(BankAccount.class).bindFromRequest();
        if(bankForm.hasErrors()) {
            return badRequest(createBankForm.render(bankForm));
        }
        bankForm.get().save();
        flash("success", "La cuenta " + bankForm.get().name + " ha sido creada");
        return GO_BANK;
    }

    public static Result deleteBank(Long id) {
        BankAccount.find.ref(id).delete();
        flash("success", "La cuenta ha sido borrada");
        return GO_BANK;
    }
}
            
