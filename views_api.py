
from a_app.decorators import jsonView


@jsonView()
def api_events(r):
    return {}



