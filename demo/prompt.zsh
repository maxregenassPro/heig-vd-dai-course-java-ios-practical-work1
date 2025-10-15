#!/bin/zsh
prompt off

# --- Function that maps the index to a readable section name ---
ind_name() {
	case "$1" in
		"0")
			echo "Introduction" ;;
		"1"|"2"|"3")
			echo "Ajout de Taches" ;;
		"4")
			echo "Lister" ;;
		"5"|"6"|"7")
			echo "Mise a Jour du Status" ;;
		|"8"|"9")
			echo "Filtrage et Triage" ;;
		"10"|"11")
			echo "Mode Global" ;;
		"12")
			echo "Effacer les Taches Accomplies" ;;
		"13")
			echo "Affichage du Fichier" ;;
		"14"|"15"|"16")
			echo "UNIX Friendly" ;;
	esac
}

# --- Function to dynamically generate the fancy prompt line ---
prompt_fancy() {
	local term_width pwd_text filler used_length remaining curr_index tot_index section_name

	term_width=$(tput cols)
	pwd_text=$(print -P "%~")  # current directory

	# --- Color configuration ---
	local LINE_COLOR="%F{cyan}"
	local PWD_COLOR="%F{yellow}"
	local INDEX_COLOR="%F{green}"
	local NAME_COLOR="%F{magenta}"
	local SECTION_COLOR="%F{blue}"
	local RESET="%f"
	# ----------------------------

	# --- Data strings ---
	curr_index=$(cat /home/sowsanti/school/repos/s3/dai/dai_pw01/demo/ind.txt)
	tot_index=$(($(wc -l < /home/sowsanti/school/repos/s3/dai/dai_pw01/demo/sequence.txt) - 1))
	section_name=$(ind_name "$curr_index")

	local plain_label=" ${section_name} ${curr_index}/${tot_index} "
	# --------------------

	# --- Left part (plain vs colored) ---
	local left_plain="─── ${pwd_text} "
	local left_colored="${LINE_COLOR}─── ${PWD_COLOR}${pwd_text}${RESET} "
	# -----------------------------------

	# --- Compute filler length ---
	used_length=$(( ${#left_plain} + ${#plain_label} + 3 ))  # +3 for trailing ───
	remaining=$(( term_width - used_length ))
	[[ $remaining -lt 0 ]] && remaining=0
	local filler=$(printf '─%.0s' {1..$remaining})
	# -----------------------------

	# --- Right section with colors ---
	local label_colored=" ${SECTION_COLOR}${section_name}${RESET} ${INDEX_COLOR}${curr_index}/${tot_index}${RESET} "
	# ---------------------------------

	# --- Final line assembly ---
	print -r -- "${left_colored}${LINE_COLOR}${filler}${label_colored}${LINE_COLOR}───${RESET}"
}

# --- Prompt lines ---
PROMPT="%K{red}%(?..%?)%k%F{magenta}>%f "
RPROMPT=''
precmd() { print -P -- "\n$(prompt_fancy)" }
