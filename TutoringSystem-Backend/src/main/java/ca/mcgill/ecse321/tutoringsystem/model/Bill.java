package ca.mcgill.ecse321.tutoringsystem.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Set;
import javax.persistence.OneToMany;

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
   private Set<Session> session;
   
   @OneToMany(mappedBy="bill" )
   public Set<Session> getSession() {
      return this.session;
   }
   
   public void setSession(Set<Session> sessions) {
      this.session = sessions;
   }
   
   }
