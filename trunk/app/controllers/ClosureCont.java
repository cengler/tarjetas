package controllers;

import play.mvc.Controller;
import play.mvc.Result;
import views.html.listClosures;

/**
 * Created with IntelliJ IDEA.
 * User: caeycae
 * Date: 28/02/13
 * Time: 23:40
 * To change this template use File | Settings | File Templates.
 */
public class ClosureCont extends Controller {

    public static Result listClosures(int page, String sortBy, String order, String filter) {
        return ok(listClosures.render(
                models.Closure.page(page, 10, sortBy, order, filter),
                sortBy, order, filter)
        );
    }
}
