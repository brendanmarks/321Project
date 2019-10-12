package ca.mcgill.ecse321.tutoringsystem.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class Bill{
   private boolean isPaid;

public void setIsPaid(boolean value) {
    this.isPaid = value;
}
public boolean isIsPaid() {
    return this.isPaid;
}
private int billId;

public void setBillId(int value) {
    this.billId = value;
}
@Id
public int getBillId() {
    return this.billId;
}
   private Session session;
   
   @OneToOne(mappedBy="bill" , optional=false)
   public Session getSession() {
      return this.session;
   }
   
   public void setSession(Session session) {
      this.session = session;
   }
   
   }
