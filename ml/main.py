from fastapi import FastAPI
import logging

from dostoevsky.tokenization import RegexTokenizer
from dostoevsky.models import FastTextSocialNetworkModel


app = FastAPI()
tokenizer = RegexTokenizer()
model = FastTextSocialNetworkModel(tokenizer=tokenizer)


@app.get("/get_emotion/")
async def send_message(messages: str):
    msgs = messages.split('$')

    predicts = model.predict(msgs, k=2)
    print(predicts)

    counter = {
        'speech': 0,
        'negative': 0,
        'skip': 0,
        'neutral': 0,
        'positive': 0
    }

    for p in predicts:
        for key in p:
            try:
                counter[key] += p[key]
            except Exception as e:
                logging.error(e)

    print(counter)
    score, emotion = max((counter[i], i) for i in counter)
    if emotion in {'skip', 'neutral', 'speech'}:
        return '😐'

    score /= len(predicts)

    if emotion == 'positive':
        if score > .5:
            return '😇'
        return '😊️'

    emts = ['🙁', '☹️', '😠', '😡', '🤬']
    for i in range(1, 6):
        if score < i / 5:
            return emts[i - 1]

    return '🤬'