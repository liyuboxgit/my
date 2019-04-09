package liyu.test.springbatch;

import java.util.List;

import org.springframework.batch.item.ItemWriter;

public class PersonWriter implements ItemWriter<Person> {
    @Override
    public void write(List<? extends Person> list) throws Exception {
        for(Person person:list){
            System.out.println(person);
        }
    }
}

