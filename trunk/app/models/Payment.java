package models;

import com.avaje.ebean.Page;
import play.data.validation.Constraints;
import play.db.ebean.Model;

import play.data.format.*;
import play.data.validation.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: caeycae
 * Date: 23/02/13
 * Time: 17:46
 * To change this template use File | Settings | File Templates.
 */
public class Payment extends Model {


    //public Long id;

    public Movement.MovementType ptype;

    public String description;

    public Double amount;

    public Date date;

    public Account account;

    public Category category;

    public Boolean installment;

    public Integer quantity;
}
