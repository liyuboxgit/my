package liyu.test.springbatch;

import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.validation.BindException;

public class PersonFieldSetMapper implements FieldSetMapper<Person> {
	 
    @Override
    public Person mapFieldSet(FieldSet fieldSet) throws BindException {
        String name = fieldSet.readRawString("name");
        int age = fieldSet.readInt("age");
        Person person = new Person();
        person.setName(name);
        person.setAge(age);
        return person;
    }
}
