import React, { Component } from 'react';
import { Button, Container, Form, FormGroup, Input, Label } from 'reactstrap';
import AppNavbar from './AppNavbar';
import { Link } from 'react-router-dom';

class EventList extends Component {
  emptyItem = {
    title: '',
    date: '',
    description: '',
    attendees: []
  };

  constructor(props) {
    super(props);
    this.state = {
      item: this.emptyItem,
    };
    this.handleChange = this.handleChange.bind(this);
    this.handleSubmit = this.handleSubmit.bind(this);
  }

  async componentDidMount() {
    if (this.props.match.params.uuid !== 'new') {
      const event = await (await fetch(`/api/event/${this.props.match.params.uuid}`)).json()
      this.setState({item: event});
    }
  }

  handleChange(event) {
    const target = event.target;
    const value = target.value;
    const name = target.name;
    let item = {...this.state.item};
    item[name] = value;
    this.setState({item});
  }

  async handleSubmit(event) {
    event.preventDefault();
    const {item} = this.state;
    item.date = new Date(item.date);
    await fetch('/api/event', {
      method: (item.uuid) ? 'PUT' : 'POST',
      headers: {
        'Accept': 'application/json',
        'Content-Type': 'application/json'
      },
      body: JSON.stringify(item),
    });
    this.props.history.push('/events');
  }

  handleAttendeeDelete (i) {
      const attendees = this.state.item.attendees.slice(0)
      attendees.splice(i, 1)
      let item = {...this.state.item};
      item[attendees] = attendees;
      this.setState({item: item, employees: this.state.employees});
    }

  handleAttendeeAdd (tag) {
    const attendees = [].concat(this.state.attendees, tag)
    let item = {...this.state.item};
    item[attendees] = attendees;
    this.setState({item: item, employees: this.state.employees })
  }

  render() {
    const {item} = this.state;
    const title = <h2>{item.uuid ? 'Edit Event' : 'Add Event'}</h2>

    return <div>
      <AppNavbar></AppNavbar>
      <Container>
        {title}
        <Form onSubmit={this.handleSubmit}>
          <FormGroup>
            <Label for="title">Title</Label>
            <Input type="text" name="title" id="title" value={item.title || ''}
              onChange={this.handleChange} autoComplete="name">
            </Input>
          </FormGroup>
          <FormGroup>
            <Label for="description">Description</Label>
            <Input type="textarea" name="description" id="description" value={item.description || ''}
              onChange={this.handleChange}>
            </Input>
          </FormGroup>
          <FormGroup>
            <Label for="date">Date</Label>
            <Input type="date" name="date" id="date"
              onChange={this.handleChange} autoComplete="name">
            </Input>
          </FormGroup>
          <FormGroup>
            <Label for="attendees">Attendees</Label>
            <Input type="select" name="attendees" id="attendees" multiple={true}>
              <option>Owais</option>
              <option>Omar</option>
              <option>Maimoona</option>
              <option>Rabbia</option>
              <option>Tahira</option>
              <option>Naveed</option>
              <option>Babar</option>
              <option>Irtiza</option>
            </Input>
          </FormGroup>

          <FormGroup>
            <Button color="primary" type="submit">Save</Button>{' '}
            <Button color="secondary" tag={Link} to="/events">Cancel</Button>
          </FormGroup>
        </Form>
      </Container>
    </div>
  }
}

export default EventList;
