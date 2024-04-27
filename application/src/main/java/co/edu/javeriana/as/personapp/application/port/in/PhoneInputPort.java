package co.edu.javeriana.as.personapp.application.port.in;


import co.edu.javeriana.as.personapp.application.port.out.PhoneOutputPort;
import co.edu.javeriana.as.personapp.common.annotations.Port;
import co.edu.javeriana.as.personapp.common.exceptions.NoExistException;
import co.edu.javeriana.as.personapp.domain.Person;
import co.edu.javeriana.as.personapp.domain.Phone;

import java.util.List;

@Port
public interface PhoneInputPort {
    public void setPersintence(PhoneOutputPort phonePersistance);

    public Phone create(Phone phone);

    public Phone edit(String phoneNumber, Phone phone) throws NoExistException;

    public Boolean drop(String phoneNumber) throws NoExistException;

    public List<Phone> findAll();

    public Phone findOne(String phoneNumber) throws NoExistException;

    public Person getOwner(String phoneNumber) throws NoExistException;
}
