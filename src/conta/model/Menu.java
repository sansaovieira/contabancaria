package conta.model;

import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

import conta.controller.ContaController;
import conta.util.Cores;

public class Menu {
	public static Scanner scanner = new Scanner(System.in);

	public static void main(String[] args) {

		ContaController contas = new ContaController();

		int opcao, numero, agencia, tipo, aniversario, numeroDestino;
		String titular;
		float saldo, limite, valor;

		while (true) {

			System.out.println(
					Cores.YELLOW + Cores.DEFAULT + "*****************************************************************");
			System.out.println("                                                        ");
			System.out.println("\n********************* 🏦 Banco Vieira💲, LDA ***************************************");
			System.out.println("                                                        ");
			System.out.println("*************************************************************************************");
			System.out.println("                                                        ");
			System.out.println("            ➕ 1 - Criar Conta                          ");
			System.out.println("            📝 2 - Listar todas as Contas               ");
			System.out.println("            🔎 3 - Buscar Conta por Numero              ");
			System.out.println("            🔎 4 - Atualizar Dados da Conta             ");
			System.out.println(Cores.RED + "            🗑 5 - Apagar Conta                         " + Cores.DEFAULT);
			System.out.println("            💸 6 - Sacar                                ");
			System.out.println("            🏧 7 - Depositar                            ");
			System.out.println("            💱 8 - Transferir valores entre Contas      ");
			System.out.println(Cores.RED + "            ⬅ 9 - Sair                                 " + Cores.DEFAULT);
			System.out.println("                                                        ");
			System.out.println("*************************************************************************************");
			System.out.println("Entre com a opção desejada:                             ");
			System.out.println("                                                     " + Cores.DEFAULT);

			try {
				opcao = scanner.nextInt();
			} catch (InputMismatchException e) {
				System.out.println("\nDigite valores inteiros!");
				scanner.nextLine();
				opcao = 0;
			}

			if (opcao == 9) {
				System.out.println(Cores.DEFAULT + "\nBanco do Brazil com Z - O seu Futuro começa aqui!");
				sobre();
				scanner.close();
				System.exit(0);
			}

			switch (opcao) {
			case 1:
				System.out.println(Cores.DEFAULT + "Criar Conta\n\n");

				System.out.println("Digite o Numero da Agência: ");
				agencia = scanner.nextInt();
				System.out.println("Digite o Nome do Titular: ");
				scanner.skip("\\R?");
				titular = scanner.nextLine();

				do {
					System.out.println("Digite o Tipo da Conta (1-CC ou 2-CP): ");
					tipo = scanner.nextInt();
				} while (tipo < 1 && tipo > 2);

				System.out.println("Digite o Saldo da Conta (R$): ");
				saldo = scanner.nextFloat();

				switch (tipo) {
				case 1 -> {
					System.out.println("Digite o Limite de Crédito (R$): ");
					limite = scanner.nextFloat();
					contas.cadastrar(new ContaCorrente(contas.gerarNumero(), agencia, tipo, titular, saldo, limite));
				}
				case 2 -> {
					System.out.println("Digite o dia do Aniversario da Conta: ");
					aniversario = scanner.nextInt();
					contas.cadastrar(
							new ContaPoupanca(contas.gerarNumero(), agencia, tipo, titular, saldo, aniversario));
				}
				}

				keyPress();
				break;
			case 2:
				System.out.println(Cores.DEFAULT + "Listar todas as Contas\n\n");

				contas.listarTodas();

				keyPress();
				break;
			case 3:
				System.out.println(Cores.GREEN + "Buscar Conta por número\n\n" + Cores.DEFAULT);

				System.out.println("Digite o número da conta: ");
				numero = scanner.nextInt();

				contas.procurarPorNumero(numero);

				keyPress();
				break;
			case 4:
				System.out.println(Cores.DEFAULT + "Atualizar dados da Conta\n\n");

				System.out.println("Digite o número da conta: ");
				numero = scanner.nextInt();

				var buscaConta = contas.buscarNaCollection(numero);

				if (buscaConta != null) {

					System.out.println("Digite o Numero da Agência: ");
					agencia = scanner.nextInt();
					System.out.println("Digite o Nome do Titular: ");
					scanner.skip("\\R?");
					titular = scanner.nextLine();

					System.out.println("Digite o Saldo da Conta (R$): ");
					saldo = scanner.nextFloat();

					tipo = buscaConta.getTipo();

					switch (tipo) {
					case 1 -> {
						System.out.println("Digite o Limite de Crédito (R$): ");
						limite = scanner.nextFloat();
						contas.atualizar(new ContaCorrente(numero, agencia, tipo, titular, saldo, limite));
					}
					case 2 -> {
						System.out.println("Digite o dia do Aniversario da Conta: ");
						aniversario = scanner.nextInt();
						contas.atualizar(new ContaPoupanca(numero, agencia, tipo, titular, saldo, aniversario));
					}
					default -> {
						System.out.println("Tipo de conta inválido!");
					}
					}

				} else
					System.out.println("\nConta não encontrada!");

				keyPress();
				break;
			case 5:
				System.out.println(Cores.DEFAULT + "Apagar a Conta\n\n");

				System.out.println("Digite o número da conta: ");
				numero = scanner.nextInt();

				contas.deletar(numero);

				keyPress();
				break;
			case 6:
				System.out.println(Cores.DEFAULT + "Saque\n\n");

				System.out.println("Digite o Numero da conta: ");
				numero = scanner.nextInt();

				do {
					System.out.println("Digite o Valor do Saque (R$): ");
					valor = scanner.nextFloat();
				} while (valor <= 0);

				contas.sacar(numero, valor);

				keyPress();
				break;
			case 7:
				System.out.println(Cores.DEFAULT + "Depósito\n\n");

				System.out.println("Digite o Numero da conta: ");
				numero = scanner.nextInt();

				do {
					System.out.println("Digite o Valor do Depósito (R$): ");
					valor = scanner.nextFloat();
				} while (valor <= 0);

				contas.depositar(numero, valor);

				keyPress();
				break;
			case 8:
				System.out.println(Cores.DEFAULT + "Transferência entre Contas\n\n");

				System.out.println("Digite o Numero da Conta de Origem: ");
				numero = scanner.nextInt();
				System.out.println("Digite o Numero da Conta de Destino: ");
				numeroDestino = scanner.nextInt();

				do {
					System.out.println("Digite o Valor da Transferência (R$): ");
					valor = scanner.nextFloat();
				} while (valor <= 0);

				contas.transferir(numero, numeroDestino, valor);

				keyPress();
				break;
			default:
				System.out.println(Cores.DEFAULT + "\nOpção Inválida!\n" + Cores.DEFAULT);
				keyPress();
				break;
			}
		}
	}

	public static void sobre() {
		System.out.println("\n*********************************************************");
		System.out.println("Projeto Desenvolvido por Sansão Dembué Vieira ");
		System.out.println("Linkedin: https://www.linkedin.com/in/sansaovieira/");
		System.out.println("https://github.com/sansaovieira");
		System.out.println("*********************************************************");
	}

	public static void keyPress() {

		try {

			System.out.println(Cores.DEFAULT + "\n\nPressione Enter para Continuar...");
			System.in.read();

		} catch (IOException e) {

			System.out.println("Você pressionou uma tecla diferente de enter!");

		}
	}

}