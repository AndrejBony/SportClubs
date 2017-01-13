package cz.muni.fi.pa165.sportsClub;

import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;
import org.dozer.loader.api.BeanMappingBuilder;
import org.dozer.loader.api.FieldsMappingOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import cz.muni.fi.pa165.sportsClub.dto.PlayerDto;
import cz.muni.fi.pa165.sportsClub.pojo.Player;

/**
 * @author Jan Tomasek
 */
@Configuration
@Import(PersistenceApplicationContext.class)
@ComponentScan(basePackages = { "cz.muni.fi.pa165.sportsClub.dao", "cz.muni.fi.pa165.sportsClub.service" })
public class ServiceApplicationContext {

	@Bean
	public Mapper dozer() {
		DozerBeanMapper dozer = new DozerBeanMapper();
		dozer.addMapping(new DozerCustomConfig());
		return dozer;
	}

	public class DozerCustomConfig extends BeanMappingBuilder {
		@Override
		protected void configure() {
			mapping(PlayerDto.class, Player.class).fields("dateOfBirth", "dateOfBirth",
					FieldsMappingOptions.copyByReference());
		}
	}
}
