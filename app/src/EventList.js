import React, { Component } from 'react';
import { Button, ButtonGroup, Container, Table } from 'reactstrap';
import AppNavbar from './AppNavbar';
import { Link } from 'react-router-dom';

class EventList extends Component {
  constructor(props) {
    super(props);
    this.state = {events: [], isLoading: true};
    this.remove = this.remove.bind(this);
  }

  componentDidMount() {
    this.setState({isLoading: true});
    fetch('api/events')
      .then(response => response.json())
      .then(data => this.setState({events: data, isLoading:  false}));
  }

  async remove(uuid) {
    await fetch(`/api/event/${uuid}`, {
      method: 'DELETE', headers: {'Accept': 'application/json', 'Content-Type': 'application/json'}
    }).then(() => {
      let updatedEvents = [...this.state.events].filter(i => i.uuid !== uuid);
      this.setState({events: updatedEvents});
    });
  }

  render() {
    const {events, isLoading} = this.state;
    if (isLoading || !events) {
      return <p>Loading...</p>;
    }
    const eventList = events.map(event => {
      const attendeeList = event.attendees.map(attendee => {
        return (attendee.name || '') + '; '
      });
      return <tr key={event.uuid}>
        <td>{event.title}</td>
        <td>{event.date}</td>
        <td>{event.description}</td>
        <td>{attendeeList}</td>
        <td>
          <ButtonGroup>
            <Button size="sm" color="primary" tag={Link} to={"/events/" + event.uuid}>Edit</Button>
            <Button size="sm" color="danger" onClick={() => this.remove(event.uuid)}>Delete</Button>
          </ButtonGroup>
        </td>
      </tr>
    });
    return (
      <div>
        <AppNavbar></AppNavbar>
          <Container fluid>
          <div className="float-right">
            <Button color="success" tag={Link} to="/events/new">Add Event</Button>
          </div>
          <h3>Groups</h3>
          <Table className="mt-4">
            <thead>
            <tr>
              <th width="20%">Title</th>
              <th width="10%">Date</th>
              <th width="20%">Description</th>
              <th width="50%">Attendees</th>
              <th width="50%">Actions</th>
            </tr>
            </thead>
            <tbody>{eventList}</tbody>
          </Table>
        </Container>
      </div>
    );
  }
}

export default EventList;
