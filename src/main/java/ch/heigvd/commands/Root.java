package ch.heigvd.commands;

import picocli.CommandLine;

@CommandLine.Command(
	description = "A small TODO List CLI to experiment with Java IOs.",
	version = "1.0.0",
	subcommands = {
		Add.class,
		List.class,
        Delete.class,
        Clear.class,
        Doing.class,
        Done.class,
	},
	scope = CommandLine.ScopeType.INHERIT,
	mixinStandardHelpOptions = true)
public class Root {
	@CommandLine.Option(
		names = {"-g", "--global"},
		description = "Act on the global (home directory) todo file rather than the CWD file.",
		scope = CommandLine.ScopeType.INHERIT)
	protected boolean globalFlag;

	public boolean getGlobalFlag() {
		return globalFlag;
	}
}
