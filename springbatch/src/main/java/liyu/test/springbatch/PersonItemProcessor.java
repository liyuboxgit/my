package liyu.test.springbatch;

import org.springframework.batch.item.ItemProcessor;

public class PersonItemProcessor implements ItemProcessor<Person,Person> {
	 
    public String inputFile;
 
    public PersonItemProcessor(){}
 
    public PersonItemProcessor(String inputFile){
        this.inputFile = inputFile;
    }
 
    @Override
    public Person process(Person person) {
        System.out.println("handle person " + person.getName());
        return person;
    }
}

