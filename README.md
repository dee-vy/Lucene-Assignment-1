# Lucene-Assignment-1

To connect to the VM:
ssh -i /Users/deekshavyas/Downloads/vyasd_key.pem azureuser@13.80.20.90
sudo su -

To clone the git:
git clone https://github.com/dee-vy/Lucene-Assignment-1.git
cd Lucene-Assignment-1/
cd assignment1/


To run shell script:
To make file executable - chmod 755 run.sh
To run the script -  sh run.sh or ./run.sh 


  
  
Alternatively, if you don't wish to run the shell script, follow these steps:-


cd trec_eval/
make
cd ..
mvn clean install
mvn compile exec:java -Dexec.mainClass="IndexFile"
cd trec_eval

./trec_eval -m map -m gm_map -m P.5 -m num_ret -m num_rel -m num_rel_ret ../cranfield/QRelsCorrectedforTRECeval ../BM25_results_StandardEnglish.txt

./trec_eval -m map -m gm_map -m P.5 -m num_ret -m num_rel -m num_rel_ret ../cranfield/QRelsCorrectedforTRECeval ../BM25_results_Classic.txt

./trec_eval -m map -m gm_map -m P.5 -m num_ret -m num_rel -m num_rel_ret ../cranfield/QRelsCorrectedforTRECeval ../BM25_results_Whitespace.txt


./trec_eval -m map -m gm_map -m P.5 -m num_ret -m num_rel -m num_rel_ret ../cranfield/QRelsCorrectedforTRECeval ../BM25_results_Simple.txt

./trec_eval -m map -m gm_map -m P.5 -m num_ret -m num_rel -m num_rel_ret ../cranfield/QRelsCorrectedforTRECeval ../VSM_results_StandardEnglish.txt

./trec_eval -m map -m gm_map -m P.5 -m num_ret -m num_rel -m num_rel_ret ../cranfield/QRelsCorrectedforTRECeval ../VSM_results_Classic.txt

./trec_eval -m map -m gm_map -m P.5 -m num_ret -m num_rel -m num_rel_ret ../cranfield/QRelsCorrectedforTRECeval ../VSM_results_Whitespace.txt

./trec_eval -m map -m gm_map -m P.5 -m num_ret -m num_rel -m num_rel_ret ../cranfield/QRelsCorrectedforTRECeval ../VSM_results_Simple.txt
