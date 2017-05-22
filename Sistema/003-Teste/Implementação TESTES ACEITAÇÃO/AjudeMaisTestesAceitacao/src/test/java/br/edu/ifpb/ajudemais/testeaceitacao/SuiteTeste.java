
package br.edu.ifpb.ajudemais.testeaceitacao;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import br.edu.ifpb.ajudemais.testeaceitacao.categoriaTest.CategoriaTest;
import br.edu.ifpb.ajudemais.testeaceitacao.instituicaoCaridadeTest.CrudInstituicaoCaridadeTest;
import br.edu.ifpb.ajudemais.testeaceitacao.loginTest.LoginTest;
import br.edu.ifpb.ajudemais.testeaceitacao.mensageiroTest.MensageiroTest;

/**
 * 
 * <p>
 * <b> SuiteTeste.java </b>
 * </p>
 *
 * <p>
 * Executa todos os testes de aceitação
 * </p>
 * 
 * @author <a href="https://github.com/JoseRafael97">Rafael Feitosa</a>
 */

@RunWith(Suite.class)

@Suite.SuiteClasses({ 
	//LoginTest.class,
	//CrudInstituicaoCaridadeTest.class,
	//CategoriaTest.class,
	MensageiroTest.class
})
public class SuiteTeste {

}
