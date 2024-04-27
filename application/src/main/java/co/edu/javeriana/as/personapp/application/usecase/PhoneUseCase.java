package co.edu.javeriana.as.personapp.application.usecase;

import co.edu.javeriana.as.personapp.application.port.in.PhoneInputPort;
import co.edu.javeriana.as.personapp.application.port.out.PhoneOutputPort;
import co.edu.javeriana.as.personapp.common.annotations.UseCase;
import co.edu.javeriana.as.personapp.common.exceptions.NoExistException;
import co.edu.javeriana.as.personapp.domain.Person;
import co.edu.javeriana.as.personapp.domain.Phone;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.List;

@Slf4j
@UseCase
public class PhoneUseCase implements PhoneInputPort {

    private PhoneOutputPort phonePersistance;

    public PhoneUseCase (@Qualifier("phoneOutputAdapterMaria") PhoneOutputPort phonePersistance) {
        this.phonePersistance = phonePersistance;
    }

    @Override
    public void setPersintence(PhoneOutputPort phonePersistance) {
        this.phonePersistance = phonePersistance;
    }

    @Override
    public Phone create(Phone phone) {
        return phonePersistance.save(phone);
    }

    @Override
    public Phone edit(String phoneNumber, Phone phone) throws NoExistException {
        Phone oldPhone = phonePersistance.findById(phoneNumber);

        if (oldPhone != null) {
            return phonePersistance.save(phone);
        }
        throw new NoExistException(
                "The phone with number " + phoneNumber + " does not exist into db, cannot be edited"
        );

    }

    @Override
    public Boolean drop(String phoneNumber) throws NoExistException {
        Phone oldPhone = phonePersistance.findById(phoneNumber);

        if (oldPhone != null) {
            return phonePersistance.delete(phoneNumber);
        }
        throw new NoExistException(
                "The phone with number " + phoneNumber + " does not exist into db, cannot be deleted"
        );
    }

    @Override
    public List<Phone> findAll() {
        log.info("Output: " + phonePersistance.getClass());
        return phonePersistance.find();
    }

    @Override
    public Phone findOne(String phoneNumber) throws NoExistException {
        Phone oldPhone = phonePersistance.findById(phoneNumber);
        if (oldPhone != null)
            return oldPhone;
        throw new NoExistException("The phone with number " + phoneNumber + " does not exist into db, cannot be found");
    }

    @Override
    public Person getOwner(String phoneNumber) throws NoExistException {
        Phone oldPhone = phonePersistance.findById(phoneNumber);
        if (oldPhone != null)
            return oldPhone.getOwner();
        throw new NoExistException("The phone with number " + phoneNumber + " does not exist into db, cannot be found");
    }
}
