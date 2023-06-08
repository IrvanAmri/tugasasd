import nltk
from collections import Counter
import dask
from dask.distributed import Client
#import subprocess

# Initialize NLTK tokenizers
tokenizer = nltk.tokenize.word_tokenize
sent_tokenizer = nltk.tokenize.sent_tokenize

@dask.delayed
def count_tokens(text):
    tokens = tokenizer(text)
    token_counts = Counter(tokens)
    return token_counts

@dask.delayed
def count_sentences(text):
    sentences = sent_tokenizer(text)
    return len(sentences)

def count_tokens_parallel(text_stream):
    client = Client()  # Start Dask client

    token_counts = Counter()
    sentence_count = 0

    tasks = []
    for text in text_stream:
        task = count_tokens(text)
        tasks.append(task)

    sentence_tasks = [count_sentences(text) for text in text_stream]

    results = dask.compute(*tasks)  # Compute the delayed tasks
    for result in results:
        token_counts.update(result)

    sentence_count = sum(dask.compute(*sentence_tasks))

    client.close()  # Close Dask client
    return token_counts, sentence_count

# Start GPU monitoring subprocess
#gpu_monitor = subprocess.Popen(['nvidia-smi', '--query-gpu=utilization.gpu', '--format=csv,noheader,nounits'],
                               #stdout=subprocess.PIPE, universal_newlines=True)

if __name__ == '__main__':
    # Read SuraBaya.txt
    with open('Sorcerers Stone.txt', 'r', encoding='cp1252', errors='ignore') as f:
        text_stream = f.readlines()

    token_counts, sentence_count = count_tokens_parallel(text_stream)
    print("Token counts:")
    for token, count in token_counts.items():
        print(token, ":", count)

    print("Number of sentences:", sentence_count)

#gpu_monitor.terminate()
