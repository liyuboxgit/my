package test;

import org.junit.Test;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.SimpleJobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.support.MapJobRepositoryFactoryBean;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.support.transaction.ResourcelessTransactionManager;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.task.SyncTaskExecutor;

import liyu.test.springbatch.Person;
import liyu.test.springbatch.PersonFieldSetMapper;
import liyu.test.springbatch.PersonItemProcessor;
import liyu.test.springbatch.PersonWriter;

public class BatchTest {
	@Test
	public void test() throws Exception {
		ResourcelessTransactionManager transactionManager = new ResourcelessTransactionManager();
		MapJobRepositoryFactoryBean jobRepositoryFactoryBean = new MapJobRepositoryFactoryBean();
        jobRepositoryFactoryBean.setTransactionManager(transactionManager);
        JobRepository jobRepository = jobRepositoryFactoryBean.getObject();
        SimpleJobLauncher jobLauncher = new SimpleJobLauncher();
        jobLauncher.setJobRepository(jobRepository);
 
        SyncTaskExecutor taskExecutor = new SyncTaskExecutor();
        jobLauncher.setTaskExecutor(taskExecutor);
 
        StepBuilderFactory stepBuilderFactory = new StepBuilderFactory(jobRepository,transactionManager);
        Step step = personStep(stepBuilderFactory,reader("person.csv"),new PersonWriter(), new PersonItemProcessor());
 
        JobBuilderFactory jobBuilderFactory = new JobBuilderFactory(jobRepository);
        Job job = jobBuilderFactory.get("job")
                .start(step)
                .build();
        jobLauncher.run(job,new JobParameters());
	}
	
	public ItemReader<Person> reader(String inputFile){
        if(inputFile == null)
            return null;
        FlatFileItemReader<Person> reader = new FlatFileItemReader<>();
        reader.setResource(new ClassPathResource(inputFile));
        reader.setLineMapper(lineMapper());
        reader.setLinesToSkip(1);
        //ExecutionContext executionContext = new ExecutionContext();
        return reader;
    }
	
	public Step personStep(StepBuilderFactory stepBuilderFactory, ItemReader<Person> reader,
            ItemWriter<Person> writer, ItemProcessor<Person,Person> processor){
		return stepBuilderFactory.get("personStep")
		 .<Person,Person>chunk(1)
		 .reader(reader)
		 .processor(processor)
		 .writer(writer)
		 .build();
	}
	
	public LineMapper<Person> lineMapper(){
        DefaultLineMapper<Person> lineMapper = new DefaultLineMapper<>();
        DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
        lineTokenizer.setDelimiter(",");
        lineTokenizer.setStrict(false);
        lineTokenizer.setNames(new String[]{"name","age"});
 
        BeanWrapperFieldSetMapper<Person> fieldSetMapper = new BeanWrapperFieldSetMapper<>();
        fieldSetMapper.setTargetType(Person.class);
        lineMapper.setLineTokenizer(lineTokenizer);
        lineMapper.setFieldSetMapper(new PersonFieldSetMapper());
        return lineMapper;
    }
}
