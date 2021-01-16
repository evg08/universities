package org.levelup.thread.deadlock;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data  //Getter,setter and so on
@AllArgsConstructor
public class Account {
    private String accountId;
    private  double amount;

}
